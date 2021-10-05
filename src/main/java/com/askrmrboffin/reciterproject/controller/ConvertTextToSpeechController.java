package com.askrmrboffin.reciterproject.controller;

import com.askrmrboffin.reciterproject.data.InputTextFromUser;
import com.askrmrboffin.reciterproject.service.S3FileUploadService;
import com.askrmrboffin.reciterproject.service.TextToSpeechService;
import com.askrmrboffin.reciterproject.service.UserService;
import com.askrmrboffin.reciterproject.utils.UserUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Controller
public class ConvertTextToSpeechController {

    @Autowired
    TextToSpeechService textToSpeechService;

    @Autowired
    S3FileUploadService s3FileUploadService;

    @Autowired
    UserService userService;

    @PostMapping("/convertTextToSpeech")
    public String convertMyTextToSpeech(@ModelAttribute("inputTextFromUser") InputTextFromUser inputTextFromUser, Authentication authentication){
        try{
            String inputStringFromUser = inputTextFromUser.getTextToConvert();
            String voiceType = inputTextFromUser.getVoiceType();
            String loggedInUser = authentication.getName();
            String timestamp = (new Timestamp(System.currentTimeMillis())).toString();
            timestamp = timestamp.replace(" ", "_");
            String uploadAudioFileName = UserUtils.sanitizeFileName(loggedInUser+"_"+timestamp)+".mp3";

            if (UserUtils.isUserInputStringValid(inputStringFromUser)) {
                textToSpeechService.testTextToSpeech(inputStringFromUser, uploadAudioFileName, loggedInUser, voiceType);
                return "redirect:/success";
            }
            return "redirect:/failure";
        }catch(Exception e){
            return "redirect:/failure";
        }
    }

    @GetMapping("/")
    public String getHomePage(Model model, Authentication authentication){
        String username = authentication.getName();
        model.addAttribute("inputTextFromUser", new InputTextFromUser());
        model.addAttribute("userAudioFileList", userService.getListOfUserAudioFiles(username));
        return "home";
    }

    @GetMapping("/success")
    public String getSuccessPage(){
        return "Success";
    }

    @GetMapping("/failure")
    public String getFailurePage(){
        return "Failure";
    }

    @GetMapping("/uploadFile")
    public String uploadASampleFileToS3Bucket(){
        boolean result = s3FileUploadService.uploadSampleFileToS3Bucket();
        if(result)
            return "redirect:/success";
        return "redirect:/failure";
    }

    @GetMapping("/file/download")
    public ResponseEntity<byte[]> downloadSampleFileFromS3Bucket(@RequestParam(name = "filename")String filename){
        byte[] result = s3FileUploadService.downloadSampleFileFromS3Bucket(filename);
        if(result != null && result.length > 0){
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+filename+"\"")
                    .body(result);
        }
        return ResponseEntity.internalServerError().body(null);
    }

    @GetMapping("/file/delete")
    public String deleteSampleFileFromS3Bucket(@RequestParam(name = "filename")String filename){
        boolean result = s3FileUploadService.deleteSampleFileFromS3Bucket(filename);
        if(result)
            return "redirect:/";
        return "redirect:/failure";
    }
}
