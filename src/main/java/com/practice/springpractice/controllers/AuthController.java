package com.practice.springpractice.controllers;

import com.practice.springpractice.dtos.LoginDto;
import com.practice.springpractice.entities.AppUser;
import com.practice.springpractice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("login")
    public AppUser login(
            @RequestBody LoginDto loginDto
    ) {

        return authService
                .verifyUser(loginDto.getUsername(), loginDto.getPassword())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

}
