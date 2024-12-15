package com.practice.springpractice.controllers;

import com.practice.springpractice.dtos.AuthResponseDto;
import com.practice.springpractice.dtos.LoginDto;
import com.practice.springpractice.dtos.UserDto;
import com.practice.springpractice.entities.AppUser;
import com.practice.springpractice.services.AuthService;
import com.practice.springpractice.services.UserService;
import com.practice.springpractice.utils.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public String test() {
        return "test";
    }

    @PostMapping("register")
    public AppUser addUser(
            @RequestBody UserDto userDto
    ) {
        Optional<AppUser> user = userService.findUserByUsername(userDto.getUsername());

        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        return userService.createUser(userDto);
    }

    @PostMapping("login")
    public AuthResponseDto login(
            @RequestBody LoginDto loginDto
    ) {
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(loginDto.getUsername(), loginDto.getPassword())
        );

        String token = JwtHelper.generateToken(authentication);

        return new AuthResponseDto(token);
    }

}
