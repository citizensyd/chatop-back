package com.chatapp.backend.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.security.Timestamp;
import java.util.Date;

@Data
public class RentalRequest {

    private Long id;

    private String name;

    private Integer surface;

    private Integer price;

    private MultipartFile picture;

    private String description;

}
