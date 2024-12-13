package com.practice.springpractice.filters;

import com.practice.springpractice.entities.AppUser;
import com.practice.springpractice.services.UserService;
import com.practice.springpractice.utils.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Autowired
    public JwtFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException, ResponseStatusException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        String token = Objects.requireNonNull(authorization).substring(7);

        String username = JwtHelper.extractUsername(token);
        Optional<AppUser> user = userService.findUserByUsername(username);

        if (user.isEmpty()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        filterChain.doFilter(request, response);

    }
}
