package com.practice.springpractice.services;


import com.practice.springpractice.dtos.UserDto;
import com.practice.springpractice.entities.AppUser;
import com.practice.springpractice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

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
    public Optional<AppUser> findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username);
    }

    /**
     *
     * @param id
     */
    public void deleteUserById(UUID id) {
        this.userRepository.deleteById(id);
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = this.userRepository.findUserByUsername(username);

        return user.map(appUser -> User.builder().username(appUser.getUsername()).password(appUser.getPassword()).roles("USER").build()).orElse(null);

    }
}
