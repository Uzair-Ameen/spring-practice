package com.practice.springpractice.controllers;

import com.practice.springpractice.dtos.AuthResponseDto;
import com.practice.springpractice.dtos.LoginDto;
import com.practice.springpractice.entities.AppUser;
import com.practice.springpractice.services.AuthService;
import com.practice.springpractice.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String test() {
        return "test";
    }


    @PostMapping("login")
    public AuthResponseDto login(
            @RequestBody LoginDto loginDto
    ) {

        AppUser user = authService
                .verifyUser(loginDto.getUsername(), loginDto.getPassword())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return new AuthResponseDto(
                JwtHelper.generateToken(loginDto.getUsername()),
                user
        );
    }

}
