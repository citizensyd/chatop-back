package com.chatapp.backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Name of the user", example = "John Doe", required = true)
    @NotBlank
    private String name;
    @Schema(description = "Email of the user", example = "john.doe@example.com", required = true)
    @Email(message = "Invalid email format")
    private String email;
    @Schema(description = "Password of the user", example = "pa$$word56", required = true)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d).*$", message = "Password must contain at least one lowercase, and one digit")
    private String password;
}
