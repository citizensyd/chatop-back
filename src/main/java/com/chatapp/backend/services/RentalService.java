package com.chatapp.backend.services;

import com.chatapp.backend.DTO.*;
import com.chatapp.backend.entity.Rental;
import com.chatapp.backend.entity.User;
import com.chatapp.backend.repository.RentalRepository;
import com.chatapp.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        dto.setOwner_id(rental.getOwner_id().getId());
        dto.setCreated_at(rental.getCreated_at());
        dto.setUpdated_at(rental.getUpdated_at());
        return dto;
    }

    public RentalsResponse getAllRentals() {
        List<Rental> rentals = repository.findAll();
        List<RentalDTO> rentalDTOs = rentals.stream().map(this::convertToDTO).collect(Collectors.toList());

        RentalsResponse response = new RentalsResponse();
        response.setRentals(rentalDTOs);

        return response;
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

    @Value("${app.upload.dir}")
    private String uploadDir;
    /**
     * Creates a new rental object.
     *
     * @param request The RentalRequest object containing the details of the rental.
     * @param file The picture file of the rental.
     * @return The created RentalResponse object.
     * @throws IOException If there is an input/output error.
     */
    public RentalResponse createRental(RentalRequest request, MultipartFile file) throws IOException {

        Optional<User> optionalAppUser = getAuthenticatedUser();
        if (!optionalAppUser.isPresent()) {
            return RentalResponse.builder()
                    .message("Utilisateur non trouvé")
                    .build();
        }
        User appUser = optionalAppUser.get();
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        Timestamp timestamp = Timestamp.valueOf(now);

        // Récupère le chemin du dossier de destination à partir de la configuration
        String repertoireImage = this.uploadDir;
        System.out.println(this.uploadDir);
        File repertoire = new File(repertoireImage);

        // Génère un nom de fichier unique (par exemple, basé sur la date actuelle)
        String nouveauNom = date + "." + file.getOriginalFilename();
        System.out.println(nouveauNom);

        String newUploadDir = "/static/images";
        String filePath = newUploadDir + "/" + nouveauNom;

        File fichierDuServeur = new File(repertoire, nouveauNom);

        System.out.println(filePath);

        // Enregistre le fichier dans le dossier de destination
        try {
            FileUtils.writeByteArrayToFile(fichierDuServeur, file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("fichier enregistré");

        var rental = Rental.builder()
                .name(request.getName())
                .surface(request.getSurface())
                .price(request.getPrice())
                .picture(filePath) // Utilise le chemin complet du fichier
                .description(request.getDescription())
                .owner_id(appUser)
                .created_at(timestamp)
                .updated_at(timestamp)
                .build();
        System.out.println(rental);
        this.repository.save(rental);

        return RentalResponse.builder()
                .message("Annonce créée avec succès")
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
                // Récupère le chemin du dossier de destination à partir de la configuration
                String uploadDir = this.uploadDir;

                // Génère un nom de fichier unique (par exemple, basé sur la date actuelle)
                String uniqueFileName = LocalDateTime.now().toString() + "_" + picture.getOriginalFilename();

                // Construit le chemin complet du fichier
                String filePath = uploadDir + File.separator + uniqueFileName;

                // Enregistre la nouvelle image dans le dossier de destination
                Files.write(Paths.get(filePath), picture.getBytes());

                // Met à jour le chemin de l'image dans l'objet Rental
                rental.setPicture(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Échec de la mise à jour de l'image", e);
            }
        });

        repository.save(rental);

        return RentalResponse.builder()
                .message("Annonce mise à jour avec succès")
                .build();
    }

}


