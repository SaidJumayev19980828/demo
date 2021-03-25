package com.example.demo.repositories;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.awt.print.Book;
import java.util.Optional;

public interface BookRepositories extends JpaRepository<Books,Long> {

  Optional<Books>findBybookName(String principal);
}
