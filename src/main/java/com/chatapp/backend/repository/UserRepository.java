package com.chatapp.backend.repository;


import com.chatapp.backend.entity.User;
import org.hibernate.sql.model.jdbc.OptionalTableUpdateOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


/**
 * The UserRepository interface is responsible for managing user data in the database.
 * It extends the CrudRepository interface and specifies the User entity and the primary key type (Integer).
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}