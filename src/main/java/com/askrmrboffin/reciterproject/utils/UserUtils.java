package com.askrmrboffin.reciterproject.utils;

import com.askrmrboffin.reciterproject.model.User;

public class UserUtils {

    public static boolean isUserValid(User user){
        if(user == null)
            return false;
        if(user.getUsername() == null || user.getUsername().length() <= 0)
            return false;
        if(user.getFirstName() == null || user.getFirstName().length() <= 0)
            return false;
        if(user.getLastName() == null || user.getLastName().length() <= 0)
            return false;
        if(user.getPassword() == null || user.getPassword().length() <= 0)
            return false;
        return true;
    }

    public static boolean isUserInputStringValid(String userInputString){
        if(userInputString == null || userInputString.length() <= 0)
            return false;
        return true;
    }

    public static String sanitizeFileName(String fileName){
        if(fileName != null && fileName.length() > 0){
            fileName = fileName.replace(" ", "_");
            fileName = fileName.replace(":", "_");
            fileName = fileName.replace(".", "_");
        }
        return fileName;
    }
}
