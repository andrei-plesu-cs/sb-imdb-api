package com.andrei.plesoianu.imdbcloneapi.security;

import com.andrei.plesoianu.imdbcloneapi.exceptions.NotFoundException;
import com.andrei.plesoianu.imdbcloneapi.models.User;
import com.andrei.plesoianu.imdbcloneapi.repositories.UserRepository;
import com.andrei.plesoianu.imdbcloneapi.security.services.UserDetailsImpl;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthUtil {
    private final UserRepository userRepository;

    public AuthUtil(@NonNull UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loggedInUser() {
        var authentication = extractAuthentication();
        var userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new NotFoundException(User.class, userDetails.getId()));
    }

    private Authentication extractAuthentication() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Objects.requireNonNull(authentication);
        return authentication;
    }
}
