package com.askrmrboffin.reciterproject.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long userId;

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String salt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserAudioFiles> userAudioFiles = new ArrayList<UserAudioFiles>();

    public User(Long userId, String username, String firstName, String lastName, String password, String salt) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.salt = salt;
    }

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<UserAudioFiles> getUserAudioFiles() {
        return userAudioFiles;
    }

    public void setUserAudioFiles(List<UserAudioFiles> userAudioFiles) {
        this.userAudioFiles = userAudioFiles;
    }

    public void addNewAudioFile(UserAudioFiles userAudioFile){
        this.userAudioFiles.add(userAudioFile);
    }
}
