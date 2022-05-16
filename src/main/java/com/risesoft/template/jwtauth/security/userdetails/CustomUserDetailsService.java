package com.risesoft.template.jwtauth.security.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private Map<String, CustomUserDetails> userMap = Map.of(
            "user1", new CustomUserDetails("user1", "password1"),
            "user2", new CustomUserDetails("user2", "password2"),
            "user3", new CustomUserDetails("user3", "password3")
    );

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMap.get(username);
    }

}
