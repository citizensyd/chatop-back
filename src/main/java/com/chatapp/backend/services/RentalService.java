package com.chatapp.backend.services;

import com.chatapp.backend.DTO.GetAllRentals;
import com.chatapp.backend.DTO.RentalDTO;
import com.chatapp.backend.DTO.RentalRequest;
import com.chatapp.backend.DTO.RentalResponse;
import com.chatapp.backend.entity.Rental;
import com.chatapp.backend.entity.User;
import com.chatapp.backend.repository.RentalRepository;
import com.chatapp.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository repository;
    private final UserRepository userRepository;

    // Pas besoin de constructeur explicite ici grâce à @RequiredArgsConstructor

    public RentalDTO convertToDTO(Rental rental) {
        RentalDTO dto = new RentalDTO();
        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());
        dto.setPicture(rental.getPicture());
        dto.setDescription(rental.getDescription());
        dto.setCreatedAt(rental.getCreatedAt());
        dto.setUpdatedAt(rental.getUpdatedAt());
        dto.setOwnerId(rental.getOwner().getId());
        return dto;
    }

    public List<RentalDTO> getAllRentals() {
        List<Rental> rentals = repository.findAll();
        return rentals.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public RentalDTO getRentalById(Long id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Location non trouvée avec l'id: " + id));
    }


    private Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userRepository.findByEmail(userDetails.getUsername());
        }
        return Optional.empty();
    }

    /**
     * Creates a new rental object.
     *
     * @param request The RentalRequest object containing the details of the rental.
     * @param file The picture file of the rental.
     * @return The created RentalResponse object.
     * @throws IOException If there is an input/output error.
     */
    public RentalResponse createRental(RentalRequest request, MultipartFile file) throws IOException {

        byte[] pictureData = file.getBytes();

        Optional<User> optionalAppUser = getAuthenticatedUser();
        if (!optionalAppUser.isPresent()) {
            return RentalResponse.builder()
                    .status("Utilisateur non trouvé")
                    .build();
        }
        User appUser = optionalAppUser.get();

        var rental = Rental.builder()
                .id(Math.toIntExact(request.getId()))
                .name(request.getName())
                .surface(request.getSurface())
                .price(request.getPrice())
                .picture(pictureData)
                .description(request.getDescription())
                .owner(appUser)
                .build();

        this.repository.save(rental);

        return RentalResponse.builder()
                .id(Math.toIntExact(rental.getId()))
                .status("Annonce créée avec succès")
                .build();
    }

    /**
     * Updates a rental object identified by its ID.
     *
     * @param id               The ID of the rental.
     * @param request          The RentalRequest object containing the updates for the rental.
     * @param optionalPicture  (Optional) The updated picture of the rental.
     * @return The updated RentalResponse object.
     * @throws EntityNotFoundException If the rental with the specified ID is not found.
     * @throws RuntimeException       If there is a failure updating the picture.
     */
    public RentalResponse updateRental(Long id, RentalRequest request, Optional<MultipartFile> optionalPicture) {
        Rental rental = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location non trouvée avec l'id: " + id));

        if (request.getName() != null && !request.getName().isEmpty()) {
            rental.setName(request.getName());
        }
        if (request.getSurface() != null) {
            rental.setSurface(request.getSurface());
        }
        if (request.getPrice() != null) {
            rental.setPrice(request.getPrice());
        }
        if (request.getDescription() != null && !request.getDescription().isEmpty()) {
            rental.setDescription(request.getDescription());
        }
        optionalPicture.ifPresent(picture -> {
            try {
                rental.setPicture(picture.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Échec de la mise à jour de l'image", e);
            }
        });

        repository.save(rental);

        return RentalResponse.builder()
                .id(Math.toIntExact(rental.getId()))
                .status("Annonce mise à jour avec succès")
                .build();
    }
}


