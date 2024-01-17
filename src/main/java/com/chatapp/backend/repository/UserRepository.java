package com.chatapp.backend.repository;


import com.chatapp.backend.entity.User;
import org.springframework.data.repository.CrudRepository;


/**
 * The UserRepository interface is responsible for managing user data in the database.
 * It extends the CrudRepository interface and specifies the User entity and the primary key type (Integer).
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);
}