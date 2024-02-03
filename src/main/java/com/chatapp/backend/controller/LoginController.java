package com.chatapp.backend.controller;

import com.chatapp.backend.DTO.AuthRequest;
import com.chatapp.backend.DTO.AuthResponse;
import com.chatapp.backend.DTO.JwtResponse;
import com.chatapp.backend.entity.User;
import com.chatapp.backend.repository.UserRepository;
import com.chatapp.backend.services.AuthenticationService;
import com.chatapp.backend.services.JWTservice;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationService service;

    /**
     * Logs in a user by authenticating their credentials and generating a JWT token.
     *
     * @param request The authentication request containing the user's email and password.
     * @return A ResponseEntity containing the generated JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @Valid @RequestBody AuthRequest request, BindingResult bindingResult
    ) {if (bindingResult.hasErrors()) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(validationErrors);
    }
        return ResponseEntity.ok(service.authenticate(request));
    }

}

