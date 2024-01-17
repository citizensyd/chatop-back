package com.chatapp.backend.controller;

import com.chatapp.backend.DTO.JwtResponse;
import com.chatapp.backend.DTO.LoginRequest;
import com.chatapp.backend.entity.User;
import com.chatapp.backend.repository.UserRepository;
import com.chatapp.backend.services.JWTservice;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final BCryptPasswordEncoder passwordEncoder;
    public JWTservice jwtService;

    public UserRepository userRepository;

    public LoginController(JWTservice jwtService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);
        // Récupérez l'utilisateur en fonction de l'email (ou nom d'utilisateur)
        System.out.println(loginRequest.getEmail());
        User user = userRepository.findByEmail(loginRequest.getEmail());
System.out.println(user);
        // Vérifiez si l'utilisateur existe
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Utilisateur introuvable");
        }

        // Comparez le mot de passe fourni avec le mot de passe enregistré en base de données
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // Mot de passe correct, générez un token JWT ou renvoyez une réponse de succès
            String token = jwtService.generateToken(user.getEmail());
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            // Mot de passe incorrect
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Mot de passe incorrect");
        }
    }

}

