package com.practice.springpractice.repositories;

import com.practice.springpractice.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {

    AppUser findUserById(UUID id);

    Optional<AppUser> findUserByUsername(String username);

    void deleteUserById(UUID id);
}
