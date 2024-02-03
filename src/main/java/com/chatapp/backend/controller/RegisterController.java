package com.chatapp.backend.controller;

import com.chatapp.backend.DTO.AuthResponse;
import com.chatapp.backend.DTO.RegisterRequest;
import com.chatapp.backend.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegisterController {

    private final AuthenticationService service;

    /**
     * Registers a new user.
     *
     * @param request        The RegisterRequest object that contains the user's name, email, and password.
     * @param bindingResult  The binding result object that holds the validation errors.
     * @return An instance of ResponseEntity with the appropriate HTTP status code and response body.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                validationErrors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(validationErrors);
        }

        return ResponseEntity.ok(service.register(request));
    }
}
