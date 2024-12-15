package com.practice.springpractice.contexts;

import com.practice.springpractice.entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDetails {

    private WebAuthenticationDetails details;

    private AppUser user;

    public static RequestDetails create(WebAuthenticationDetails details, AppUser user) {
        return new RequestDetails(details, user);
    }
}
