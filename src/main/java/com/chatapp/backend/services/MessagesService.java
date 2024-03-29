package com.chatapp.backend.services;

import com.chatapp.backend.DTO.MessageRequest;
import com.chatapp.backend.DTO.MessageResponse;
import com.chatapp.backend.DTO.RentalResponse;
import com.chatapp.backend.entity.Message;
import com.chatapp.backend.entity.Rental;
import com.chatapp.backend.entity.User;
import com.chatapp.backend.repository.MessageRepository;
import com.chatapp.backend.repository.RentalRepository;
import com.chatapp.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessagesService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    /**
     * Creates a new message.
     *
     * @param request The message request object.
     * @return The message response object.
     */
    public MessageResponse createMessage(MessageRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> optionalAppUser = userRepository.findByEmail(userDetails.getUsername());
            if (!optionalAppUser.isPresent()) {
                return MessageResponse.builder()
                        .message("Utilisateur non trouvé")
                        .build();
            }
            User appUser = optionalAppUser.get();
            Optional<Rental> optionalRental = rentalRepository.findById(Math.toIntExact(request.getRental_id()));
            if (!optionalRental.isPresent()) {
                return MessageResponse.builder()
                        .message("Location non trouvée")
                        .build();
            }
            Rental rental = optionalRental.get();
            var message = Message.builder()
                    .message(request.getMessage())
                    .user(appUser)
                    .rental(rental)
                    .build();
            this.messageRepository.save(message);

            return MessageResponse.builder()
                    .message("Message envoyé avec succès")
                    .build();
        } else {
            return MessageResponse.builder()
                    .message("Échec de l'authentification")
                    .build();
        }
    }
}
