package com.chatapp.backend.DTO;

import org.springframework.http.HttpStatus;

public class CustomErrorResponse {
    private int status; // Le code de statut HTTP
    private String message; // Le message d'erreur à afficher
    private long timestamp; // Le moment où l'erreur s'est produite

    // Constructeur prenant un HttpStatus et un message d'erreur.
    public CustomErrorResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters et Setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

