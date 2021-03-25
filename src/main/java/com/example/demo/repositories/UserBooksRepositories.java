package com.example.demo.repositories;

import com.example.demo.entity.Books;
import com.example.demo.entity.User;
import com.example.demo.entity.UserBooks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBooksRepositories extends JpaRepository<UserBooks,Long> {


    List<UserBooks>findAllByUser(User userPhoneNumber) ;

}
