package com.chatapp.backend.repository;


import com.chatapp.backend.entity.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {

}