package com.askrmrboffin.reciterproject.repository;

import com.askrmrboffin.reciterproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user FROM User user WHERE user.username = ?1")
    User findUserByUsername(String username);
}
