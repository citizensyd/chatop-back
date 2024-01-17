package com.chatapp.backend.services;

import com.chatapp.backend.DTO.RegisterRequest;
import com.chatapp.backend.entity.User;
import com.chatapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

/**
 * The UserService class handles user registration.
 */
@Service
public class UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;
    private JwtEncoder jwtEncoder;


    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
    }

    public void registerUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);
    }

}
