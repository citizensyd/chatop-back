package com.chatapp.backend.controller;

import com.chatapp.backend.DTO.RentalDTO;
import com.chatapp.backend.DTO.RentalRequest;
import com.chatapp.backend.DTO.RentalResponse;
import com.chatapp.backend.services.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService service;

    @GetMapping("/")
    public ResponseEntity<List<RentalDTO>> getAll() {
        List<RentalDTO> rentals = service.getAllRentals();
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getById(@PathVariable Long id) {
        RentalDTO rental = service.getRentalById(id);
        return ResponseEntity.ok(rental);
    }

    @PostMapping("/{id}")
    public ResponseEntity<RentalResponse> create(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("surface") int surface,
            @RequestParam("price") int price,
            @RequestParam("description") String description,
            @RequestPart("picture") MultipartFile picture) throws IOException {
        RentalRequest request = new RentalRequest();
        request.setId(id);
        request.setName(name);
        request.setSurface(surface);
        request.setPrice(price);
        request.setDescription(description);
        request.setId(id);
        return ResponseEntity.ok(service.createRental(request, picture));
    }




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
