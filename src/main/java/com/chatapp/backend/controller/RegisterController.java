package com.chatapp.backend.controller;

import com.chatapp.backend.DTO.JwtResponse;
import com.chatapp.backend.DTO.LoginRequest;
import com.chatapp.backend.DTO.RegisterRequest;
import com.chatapp.backend.services.JWTservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class RegisterController {

    public RegisterController(JWTservice jwtService) {
        this.jwtService = jwtService;
    }
    public JWTservice jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (registerRequest.getEmail() != null &&
                registerRequest.getEmail() instanceof String &&
                registerRequest.getPassword() != null &&
                registerRequest.getPassword() instanceof String &&
                registerRequest.getName() != null && registerRequest.getName() instanceof String) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getPassword());
            String token = jwtService.generateToken(authentication);
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Bad request, wrong credentials");
        }
    }

}
