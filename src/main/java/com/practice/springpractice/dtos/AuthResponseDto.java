package com.practice.springpractice.dtos;

import com.practice.springpractice.entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {

    private String token;
}

