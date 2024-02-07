package com.chatapp.backend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The RegisterRequest class represents a request to register a new user.
 * It contains the user's name, email, and password.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String name;
    @Email(message = "Invalid email format")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$", message = "Password must contain at least one uppercase, one lowercase, and one digit")
    private String password;
}
