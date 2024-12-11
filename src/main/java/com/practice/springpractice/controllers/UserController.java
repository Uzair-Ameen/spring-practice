package com.practice.springpractice.controllers;


import com.practice.springpractice.dtos.UserDto;
import com.practice.springpractice.entities.AppUser;
import com.practice.springpractice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<AppUser> testApi() {
        return this.userService.findAll();
    }

    @PostMapping
    public AppUser addUser(
            @RequestBody UserDto userDto
    ) {
        return this.userService.createUser(userDto);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") UUID id) {
        this.userService.deleteUserById(id);
    }

}
