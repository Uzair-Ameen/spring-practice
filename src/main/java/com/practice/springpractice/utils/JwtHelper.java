package com.practice.springpractice.utils;

import com.practice.springpractice.constants.JwtConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.Map;

public class JwtHelper {


    private static final JwtBuilder jwtBuilder =
            Jwts.builder()
            .expiration(new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME))
            .signWith(Keys.hmacShaKeyFor(JwtConstants.SECRET.getBytes()));


    public static String generateToken(Authentication authentication) {

        String username = authentication.getName();

        return jwtBuilder
                .subject(username)
                .claims(Map.of(
                        "role", "TEST_ROLE"
                ))
                .compact();
    }

    public static String extractUsername(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(JwtConstants.SECRET.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public static String extractJwtFromRequest(HttpServletRequest request) throws AuthenticationException {
        String authorization = request.getHeader("Authorization");

        if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer ")) {
            throw new AuthenticationException("Missing Authorization header");
        }

        return authorization.substring(7);
    }
}
