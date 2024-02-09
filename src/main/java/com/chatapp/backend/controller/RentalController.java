package com.chatapp.backend.controller;

import com.chatapp.backend.DTO.RentalDTO;
import com.chatapp.backend.DTO.RentalRequest;
import com.chatapp.backend.DTO.RentalResponse;
import com.chatapp.backend.DTO.RentalsResponse;
import com.chatapp.backend.services.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService service;

    /**
     * Retrieves all rental objects.
     *
     * @return A ResponseEntity object containing a list of RentalDTO objects.
     */
    @GetMapping("")
    public ResponseEntity<RentalsResponse> getAll() {
        RentalsResponse rentalsResponse = service.getAllRentals();
        return ResponseEntity.ok(rentalsResponse);
    }

    /**
     * Retrieves a RentalDTO object by its ID.
     *
     * @param id The ID of the rental.
     * @return A ResponseEntity object containing the RentalDTO object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getById(@PathVariable Long id) {
        RentalDTO rental = service.getRentalById(id);
        return ResponseEntity.ok(rental);
    }


    /**
     * Creates a new rental object.
     *
     * @param name The name of the rental.
     * @param surface The surface area of the rental.
     * @param price The price of the rental.
     * @param description The description of the rental.
     * @param picture The picture of the rental.
     * @return A ResponseEntity object containing the created RentalResponse object.
     * @throws IOException If an input/output error occurs.
     */
    @PostMapping("/{id}")
    public ResponseEntity<RentalResponse> create(
            @RequestParam("name") String name,
            @RequestParam("surface") int surface,
            @RequestParam("price") int price,
            @RequestParam("description") String description,
            @RequestPart("picture") MultipartFile picture) throws IOException {
        RentalRequest request = new RentalRequest();
        request.setName(name);
        request.setSurface(surface);
        request.setPrice(price);
        request.setDescription(description);
        return ResponseEntity.ok(service.createRental(request, picture));
    }

    /**
     * Updates a rental object identified by its ID.
     *
     * @param id          The ID of the rental.
     * @param name        (Optional) The name of the rental.
     * @param surface     (Optional) The surface area of the rental.
     * @param price       (Optional) The price of the rental.
     * @param description (Optional) The description of the rental.
     * @param picture     (Optional) The picture of the rental.
     * @return A ResponseEntity object containing the updated RentalResponse object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RentalResponse> update(
            @PathVariable Long id,
            @RequestParam(name = "name", required = false) Optional<String> name,
            @RequestParam(name = "surface", required = false) Optional<Integer> surface,
            @RequestParam(name = "price", required = false) Optional<Integer> price,
            @RequestParam(name = "description", required = false) Optional<String> description,
            @RequestPart(name = "picture", required = false) Optional<MultipartFile> picture) {

        RentalRequest request = new RentalRequest();
        request.setId(id);

        name.ifPresent(request::setName);
        surface.ifPresent(request::setSurface);
        price.ifPresent(request::setPrice);
        description.ifPresent(request::setDescription);

        return ResponseEntity.ok(service.updateRental(id, request, picture));
    }
}
