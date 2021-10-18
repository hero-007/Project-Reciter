package com.askrmrboffin.reciterproject.data;

public class InputTextFromUser {
    private String textToConvert;
    private String voiceType;
    private String userProvidedFilename;

    public InputTextFromUser(String textToConvert, String voiceType, String userProvidedFilename) {
        this.textToConvert = textToConvert;
        this.voiceType = voiceType;
        this.userProvidedFilename = userProvidedFilename;
    }

    public InputTextFromUser() {
    }

    public String getTextToConvert() {
        return textToConvert;
    }

    public void setTextToConvert(String textToConvert) {
        this.textToConvert = textToConvert;
    }

    public String getVoiceType() {
        return voiceType;
    }

    public void setVoiceType(String voiceType) {
        this.voiceType = voiceType;
    }

    public String getUserProvidedFilename() {
        return userProvidedFilename;
    }

    public void setUserProvidedFilename(String userProvidedFilename) {
        this.userProvidedFilename = userProvidedFilename;
    }
}
