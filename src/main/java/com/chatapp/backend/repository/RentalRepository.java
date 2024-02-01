package com.chatapp.backend.repository;

import com.chatapp.backend.entity.Rental;
import com.chatapp.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    Optional<Rental> findById(Long rental_id);
}
