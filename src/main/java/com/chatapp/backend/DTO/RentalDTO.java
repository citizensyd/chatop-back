package com.chatapp.backend.DTO;

import lombok.Data;

import java.security.Timestamp;

@Data
public class RentalDTO {

    private Integer id;
    private String name;
    private Integer surface;
    private Integer price;
    private byte[] picture;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer ownerId;

    public RentalDTO() {
    }


}

