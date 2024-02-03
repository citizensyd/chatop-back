package com.chatapp.backend.controller;

import com.chatapp.backend.DTO.CustomErrorResponse;
import com.chatapp.backend.exceptions.InvalidCredentialsException;
import com.chatapp.backend.exceptions.JwtAuthenticationException;
import com.chatapp.backend.exceptions.ResourceNotFoundException;
import com.chatapp.backend.exceptions.TokenNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;


@ControllerAdvice
public class GlobalExceptionHandler {

    // Gestion des identifiants invalides
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<CustomErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        CustomErrorResponse response = new CustomErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleTokenNotFoundException(TokenNotFoundException ex) {
        CustomErrorResponse response = new CustomErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Gestion des erreurs JWT (à créer selon vos besoins)
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<CustomErrorResponse> handleJwtAuthenticationException(JwtAuthenticationException ex) {
        CustomErrorResponse response = new CustomErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // Gestion de l'accès non autorisé
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        CustomErrorResponse response = new CustomErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorized");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // Gestion des ressources non trouvées
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        CustomErrorResponse response = new CustomErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    // ... autres gestionnaires d'exceptions si nécessaire
}

