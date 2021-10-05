package com.askrmrboffin.reciterproject.data;

public class InputTextFromUser {
    private String textToConvert;
    private String voiceType;

    public InputTextFromUser(String textToConvert, String voiceType) {
        this.textToConvert = textToConvert;
        this.voiceType = voiceType;
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
}
