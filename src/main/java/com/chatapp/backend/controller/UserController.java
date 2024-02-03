package com.chatapp.backend.controller;

import com.chatapp.backend.entity.User;
import com.chatapp.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/")
public class UserController {

    private final UserService userService; // Supposons que vous ayez un UserService

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves the details of a user.
     *
     * @param authenticatedUser The authenticated user.
     * @param id                The ID of the user.
     * @return A ResponseEntity object containing the user details or an error message.
     */
    @GetMapping("user/{id}")
    public ResponseEntity<?> returnUserDetails(@AuthenticationPrincipal User authenticatedUser, @PathVariable Integer id) {
        if (authenticatedUser != null) {
            User user = userService.getUserById(Math.toIntExact(id));
            if (user != null) {
                Map<String, String> userInfo = new HashMap<>();
                userInfo.put("username", user.getUsername());
                userInfo.put("email", user.getEmail());
                return ResponseEntity.ok(userInfo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
    }
}
