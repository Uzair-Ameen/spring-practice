package com.practice.springpractice.services;


import com.practice.springpractice.dtos.UserDto;
import com.practice.springpractice.entities.AppUser;
import com.practice.springpractice.entities.Role;
import com.practice.springpractice.enums.RoleEnum;
import com.practice.springpractice.repositories.RoleRepository;
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
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

        Optional<Role> userRole =  roleRepository.findByName(RoleEnum.USER);

        AppUser user = new AppUser(userDto, userRole.orElse(null));

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
        Optional<AppUser> appUser = this.userRepository.findUserByUsername(username);

        return appUser.map(
                user ->
                        User
                                .builder()
                                .username(user.getUsername())
                                .password(user.getPassword())
                                .authorities(user.getAuthorities())
                                .build()
        ).orElseThrow(() -> new UsernameNotFoundException(username));

    }
}
