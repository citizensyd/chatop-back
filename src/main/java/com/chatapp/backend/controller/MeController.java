package com.chatapp.backend.controller;

import com.chatapp.backend.DTO.UpdateUserRequest;
import com.chatapp.backend.entity.User;
import com.chatapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class MeController {


    @GetMapping("/me")
    public ResponseEntity<?> returnUserDetails(@AuthenticationPrincipal User authenticatedUser) {
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié");
        }

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", authenticatedUser.getUsernameActual()); // ou getEmail, selon votre implémentation UserDetails
        userDetails.put("email", authenticatedUser.getEmail());

        return ResponseEntity.ok(userDetails);
    }

}
