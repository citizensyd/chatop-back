package com.chatapp.backend.controller;

import com.chatapp.backend.DTO.JwtResponse;
import com.chatapp.backend.DTO.LoginRequest;
import com.chatapp.backend.services.JWTservice;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    public JWTservice jwtService;

    public LoginController(JWTservice jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Tentative de connexion pour l'email: " + loginRequest.getEmail() + loginRequest.getPassword());
        if ("test@test.com".equals(loginRequest.getEmail()) && "test!31".equals(loginRequest.getPassword())) {
            System.out.println("Tentative de connexion pour l'email: " + loginRequest.getEmail());
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            System.out.println(authentication);
            String token = jwtService.generateToken(authentication);
            System.out.println(token);
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Bad request, wrong credentials");
        }
    }


}
