package com.askrmrboffin.reciterproject.service;

import com.askrmrboffin.reciterproject.model.User;
import com.askrmrboffin.reciterproject.model.UserAudioFiles;
import com.askrmrboffin.reciterproject.repository.UserRepository;
import com.askrmrboffin.reciterproject.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HashService hashService;

    public Integer createNewUser(User newUser){
        if(UserUtils.isUserValid(newUser)){
            SecureRandom random = new SecureRandom();
            byte salt[] = new byte[16];
            random.nextBytes(salt);
            String encodedSalt = Base64.getEncoder().encodeToString(salt);
            String hashedPassword = hashService.getHashedValue(newUser.getPassword(), encodedSalt);
            try{
                userRepository.save(new User(null, newUser.getUsername(), newUser.getFirstName(), newUser.getLastName(), hashedPassword, encodedSalt));
            }catch(Exception e){
                return null;
            }
        }
        return null;
    }

    public List<String> getListOfUserAudioFiles(String username){
        if(username != null && username.length() > 0){
            User user = userRepository.findUserByUsername(username);
            if(user != null){
                List<UserAudioFiles> userAudioFiles = user.getUserAudioFiles();
                List<String> fileNameList = new ArrayList<String>();

                for(UserAudioFiles file : userAudioFiles){
                    fileNameList.add(file.getUploadedFileName());
                }

                return fileNameList;
             }
        }
        return null;
    }
}
