package com.practice.springpractice.services;


import com.practice.springpractice.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;

    @Autowired
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public Optional<AppUser> verifyUser(String username, String password) {
        return userService.findUserByUsername(username).filter(u -> u.isPasswordValid(password));
    }
}
