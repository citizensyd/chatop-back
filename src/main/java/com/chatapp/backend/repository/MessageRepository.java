package com.chatapp.backend.repository;

import com.chatapp.backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The MessageRepository interface extends the JpaRepository interface to provide CRUD operations for the Message entity in the _message table.
 *
 * @see JpaRepository
 * @see Message
 * @see Integer
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {


}