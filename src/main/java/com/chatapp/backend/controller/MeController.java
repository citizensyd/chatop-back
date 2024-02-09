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


    /**
     * Returns the details of the authenticated user.
     *
     * @param authenticatedUser the authenticated user
     * @return a ResponseEntity containing the user details as a Map. The Map contains the following key-value pairs:
     *         - username: the actual username of the user
     *         - email: the email of the user
     *         If the authenticated user is null, returns a ResponseEntity with status UNAUTHORIZED and the message "Utilisateur non authentifié".
     */
    @GetMapping("/me")
    public ResponseEntity<?> returnUserDetails(@AuthenticationPrincipal User authenticatedUser) {
        if (authenticatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié");
        }

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("name", authenticatedUser.getName());
        userDetails.put("email", authenticatedUser.getEmail());
        userDetails.put("created_at", authenticatedUser.getCreated_at());
        userDetails.put("updated_at", authenticatedUser.getUpdated_at());

        return ResponseEntity.ok(userDetails);
    }

}
