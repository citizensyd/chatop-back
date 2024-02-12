package com.chatapp.backend.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RentalRequest {

    @Schema(description = "ID of the rental property")
    private Long id;

    @Schema(description = "Name of the rental property", example= "location")
    private String name;

    @Schema(description = "Surface area of the rental property", example= "56")
    private Integer surface;

    @Schema(description = "Price of the rental property", example= "74")
    private Integer price;

    @Schema(description = "Picture of the rental property", example= "http://www.cloudynaryerhjbfzeryf.com")
    private String picture;

    @Schema(description = "Description of the rental property", example= "lorem ipsum")
    private String description;
}
