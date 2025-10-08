package com.andrei.plesoianu.imdbcloneapi.controllers;

import com.andrei.plesoianu.imdbcloneapi.security.jwt.JwtUtils;
import com.andrei.plesoianu.imdbcloneapi.security.request.LoginRequest;
import com.andrei.plesoianu.imdbcloneapi.security.response.UserInfoResponse;
import com.andrei.plesoianu.imdbcloneapi.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            @NonNull JwtUtils jwtUtils,
            @NonNull AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        UserInfoResponse userInfoResponse = UserInfoResponse.of(userDetails);
        userInfoResponse.setToken(jwtUtils.generateTokenFromUsername(userDetails));

        return ResponseEntity.ok(userInfoResponse);
    }
}
