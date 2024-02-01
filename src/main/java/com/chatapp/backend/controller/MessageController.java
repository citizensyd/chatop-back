package com.chatapp.backend.controller;

import com.chatapp.backend.DTO.MessageRequest;
import com.chatapp.backend.DTO.MessageResponse;
import com.chatapp.backend.services.MessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")

public class MessageController {

    @Autowired
    private  MessagesService service;


    @PostMapping()
    public ResponseEntity<MessageResponse> message(@RequestBody MessageRequest request) {
        MessageResponse response = service.createMessage(request);
        return ResponseEntity.ok(response);
    }
}

