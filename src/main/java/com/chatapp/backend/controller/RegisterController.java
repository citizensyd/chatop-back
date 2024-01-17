package com.chatapp.backend.controller;

import com.chatapp.backend.DTO.JwtResponse;
import com.chatapp.backend.DTO.RegisterRequest;
import com.chatapp.backend.services.JWTservice;
import com.chatapp.backend.services.UserService;
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

    private final JWTservice jwtService;
    private final UserService userService;

    public RegisterController(JWTservice jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (registerRequest != null && validateRegisterRequest(registerRequest)) {
            userService.registerUser(registerRequest);
            Authentication authentication = new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getPassword());
            String token = jwtService.generateToken(String.valueOf(authentication));
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Bad request, wrong credentials");
        }
    }

    private boolean validateRegisterRequest(RegisterRequest registerRequest) {
        // Implémentez ici la logique de validation pour le RegisterRequest
        // Vous pouvez vérifier que les champs requis sont présents et valides
        // Retournez true si la validation réussit, sinon false
        // Vous pouvez également gérer d'autres règles de validation personnalisées ici
        return true; // À modifier en fonction de vos besoins
    }
}
