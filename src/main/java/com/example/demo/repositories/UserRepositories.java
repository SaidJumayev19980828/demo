package com.example.demo.repositories;

import com.example.demo.entity.Books;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositories extends JpaRepository<User,Long> {

    Optional<User>findByPhoneNumber(String principal);
}
