package com.chatapp.backend.services;

import com.chatapp.backend.DTO.AuthRequest;
import com.chatapp.backend.DTO.AuthResponse;
import com.chatapp.backend.DTO.RegisterRequest;
import com.chatapp.backend.entity.Role;
import com.chatapp.backend.entity.User;
import com.chatapp.backend.exceptions.DatabaseErrorException;
import com.chatapp.backend.exceptions.EmailAlreadyUsedException;
import com.chatapp.backend.exceptions.InvalidCredentialsException;
import com.chatapp.backend.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTservice jwtService;

    LocalDateTime now = LocalDateTime.now();
    Timestamp timestamp = Timestamp.valueOf(now);

    /**
     * Registers a new user.
     *
     * @param request The RegisterRequest object that contains the user's name, email, and password.
     * @return An instance of AuthResponse that contains the generated JWT token.
     * @throws EmailAlreadyUsedException if the email is already in use.
     * @throws DatabaseErrorException if there is a database error during registration.
     */
    public AuthResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            logger.warn("Tentative d'enregistrement avec un email déjà utilisé : {}", request.getEmail());
            throw new EmailAlreadyUsedException("Email is already in use");
        }

        try {
            var user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .created_at(timestamp)
                    .updated_at(timestamp)
                    .role(Role.USER)
                    .build();
            repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            logger.info("Nouvel utilisateur enregistré avec succès : {}", user.getEmail());
            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur lors de l'enregistrement de l'utilisateur", e);
            throw new DatabaseErrorException("Failed to register user due to a database error");
        }
    }

    /**
     * Authenticates a user by verifying their credentials and generates a JWT token.
     *
     * @param request The authentication request containing the user's email and password.
     * @return An instance of AuthResponse that contains the generated JWT token.
     * @throws InvalidCredentialsException if the email or password is invalid.
     * @throws UsernameNotFoundException if the user is not found with the given email.
     */
    public AuthResponse authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getLogin(),
                            request.getPassword()
                    )
            );
            logger.info("Authentification réussie pour l'email : {}", request.getLogin());
        } catch (AuthenticationException e) {
            logger.error("Échec de l'authentification pour l'email : {}", request.getLogin(), e);
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        User user = repository.findByEmail(request.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getLogin()));

        var jwtToken = jwtService.generateToken(user);
        logger.info("Token JWT généré pour l'utilisateur : {}", request.getLogin());
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

}
