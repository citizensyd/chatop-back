package com.chatapp.backend.DTO;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class RentalDTO {

    private Integer id;
    private String name;
    private Integer surface;
    private Integer price;
    private String picture;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Integer owner_id;

    public RentalDTO() {
    }


}

