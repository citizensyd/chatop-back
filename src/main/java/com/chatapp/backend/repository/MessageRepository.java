package com.chatapp.backend.repository;

import com.chatapp.backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message, Integer> {


}