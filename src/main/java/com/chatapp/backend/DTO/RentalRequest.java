package com.chatapp.backend.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RentalRequest {

    private Long id;

    private String name;

    private Integer surface;

    private Integer price;

    private MultipartFile picture;

    private String description;

}
