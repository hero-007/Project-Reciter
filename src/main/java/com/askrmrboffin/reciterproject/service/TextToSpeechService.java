package com.askrmrboffin.reciterproject.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.askrmrboffin.reciterproject.model.User;
import com.askrmrboffin.reciterproject.model.UserAudioFiles;
import com.askrmrboffin.reciterproject.repository.UserAudioFilesRepository;
import com.askrmrboffin.reciterproject.repository.UserRepository;
import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class TextToSpeechService {

    @Autowired
    UserAudioFilesRepository userAudioFilesRepository;

    @Autowired
    UserRepository userRepository;

    public void testTextToSpeech(String textToConvert, String uploadFileName, String username, String voiceType) throws IOException {
        // Instantiates a client
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            // Set the text input to be synthesized
            SynthesisInput input = SynthesisInput.newBuilder().setText(textToConvert).build();

            // Build the voice request, select the language code ("en-US") and the ssml voice gender
            // ("neutral")
            SsmlVoiceGender ssmlVoiceGender;
            if(voiceType.equals("male"))
                ssmlVoiceGender = SsmlVoiceGender.MALE;
            else if(voiceType.equals("female"))
                ssmlVoiceGender = SsmlVoiceGender.FEMALE;
            else
                ssmlVoiceGender = SsmlVoiceGender.NEUTRAL;

            VoiceSelectionParams voice =
                    VoiceSelectionParams.newBuilder()
                            .setLanguageCode("en-US")
                            .setSsmlGender(ssmlVoiceGender)
                            .build();

            // Select the type of audio file you want returned
            AudioConfig audioConfig =
                    AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();

            // Perform the text-to-speech request on the text input with the selected voice parameters and
            // audio file type
            SynthesizeSpeechResponse response =
                    textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            // Get the audio contents from the response
            ByteString audioContents = response.getAudioContent();

            // Write the response to the output file.
            /**
            try (OutputStream out = new FileOutputStream(".\\output\\output.mp3")) {
                out.write(audioContents.toByteArray());
                System.out.println("Audio content written to file \"output.mp3\"");
            }
             **/

            // Uploading this output file to s3 bucket
            Regions clientRegion = Regions.US_EAST_2;
            String bucketName = "reciter-output-files";
            String stringObjKeyName = uploadFileName;

            try {
                AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                        .withRegion(clientRegion)
                        .build();

                InputStream inputStream = new ByteArrayInputStream(audioContents.toByteArray());
                s3Client.putObject(bucketName, stringObjKeyName, inputStream, new ObjectMetadata());

                // After this file has been uploaded to S3 - add the respective entry in the UserAudioFiles table
                User user = userRepository.findUserByUsername(username);
                if(user != null){
                    UserAudioFiles newUserAudioFile = new UserAudioFiles(null, user, uploadFileName);
                    userAudioFilesRepository.save(newUserAudioFile);
                }
            }catch (AmazonServiceException e){
                e.printStackTrace();
            }catch (SdkClientException e){
                e.printStackTrace();
            }
        }
    }
}
