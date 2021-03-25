package com.example.demo.repositories;

import com.example.demo.entity.Books;
import com.example.demo.entity.HomePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource
public interface HomePageBooks extends JpaRepository<HomePage,Long> {
    List<HomePage>findByName(String name);
}
