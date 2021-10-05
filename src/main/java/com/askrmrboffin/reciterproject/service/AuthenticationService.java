package com.askrmrboffin.reciterproject.service;

import com.askrmrboffin.reciterproject.model.User;
import com.askrmrboffin.reciterproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HashService hashService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findUserByUsername(username);

        if(user != null){
            String encodedSalt = user.getSalt();
            String hashedPassword = hashService.getHashedValue(password, encodedSalt);
            if(hashedPassword.equals(user.getPassword())){
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return  authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
