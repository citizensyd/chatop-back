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




    public MessageResponse createMessage(MessageRequest request) {
        System.out.println("Service createMessage");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> optionalAppUser = userRepository.findByEmail(userDetails.getUsername());
            if (!optionalAppUser.isPresent()) {
                return MessageResponse.builder()
                        .status("Utilisateur non trouvé")
                        .build();
            }
            User appUser = optionalAppUser.get();
            System.out.println("ligne 41 message Service");
            System.out.println(appUser);
            Optional<Rental> optionalRental = rentalRepository.findById(Math.toIntExact(request.getRental_id()));
            if (!optionalRental.isPresent()) {
                return MessageResponse.builder()
                        .status("Location non trouvée")
                        .build();
            }
            Rental rental = optionalRental.get();
            System.out.println("ligne 49 rental Service");
            System.out.println(rental);
            var message = Message.builder()
                    .message(request.getMessage())
                    .user(appUser)
                    .rental(rental)
                    .build();
            this.messageRepository.save(message);

            return MessageResponse.builder()
                    .id(Math.toIntExact(message.getId()))
                    .status("Message envoyé avec succès")
                    .build();
        } else {
            return MessageResponse.builder()
                    .status("Échec de l'authentification")
                    .build();
        }
    }
}
