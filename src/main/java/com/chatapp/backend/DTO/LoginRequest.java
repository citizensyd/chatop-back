package com.chatapp.backend.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String password;
}

