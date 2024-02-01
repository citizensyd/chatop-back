package com.chatapp.backend.repository;

import com.chatapp.backend.entity.Rental;
import com.chatapp.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * RentalRepository is an interface that extends the JpaRepository interface
 * to provide CRUD operations for the Rental entity in the _rental table.
 * It also includes a custom method findById to retrieve a Rental by its rental_id.
 *
 * @see JpaRepository
 * @see Rental
 * @see Optional
 */
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    Optional<Rental> findById(Long rental_id);
}
