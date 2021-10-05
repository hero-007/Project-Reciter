package com.askrmrboffin.reciterproject.repository;

import com.askrmrboffin.reciterproject.model.UserAudioFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAudioFilesRepository extends JpaRepository<UserAudioFiles, Long> {

    @Query("SELECT audioFile FROM UserAudioFiles audioFile WHERE audioFile.user.username = ?1")
    List<UserAudioFiles> findAudioFilesForUser(String username);

    @Query("SELECT audioFile FROM UserAudioFiles audioFile WHERE audioFile.uploadedFileName = ?1")
    UserAudioFiles findAudioFileByName(String fileName);
}
