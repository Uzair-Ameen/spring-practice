package com.practice.springpractice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    @Email
    @NotBlank
    private String username;

    @Size(min = 6, max = 20)
    @NotBlank
    private String password;

    @NotBlank
    private String firstName;
    private String lastName;
}
