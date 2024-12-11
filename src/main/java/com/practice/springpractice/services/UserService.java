package com.practice.springpractice.services;


import com.practice.springpractice.dtos.UserDto;
import com.practice.springpractice.entities.AppUser;
import com.practice.springpractice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *
     * @return
     */
    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    /**
     *
     * @param userDto
     * @return
     */
    public AppUser createUser(UserDto userDto) {
        AppUser user = new AppUser(userDto.getUsername(), userDto.getPassword(), userDto.getFirstName(), userDto.getLastName());

        System.out.println(user.getUsername() + " " + user.getPassword() + " " + user.getFirstName() + " " + user.getLastName() + " " + user.getCreatedAt());

        return userRepository.save(user);
    }

    /**
     *
     * @param username
     * @return
     */
    public AppUser findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username);
    }

    /**
     *
     * @param id
     */
    public void deleteUserById(UUID id) {
        this.userRepository.deleteById(id);
    }
}
