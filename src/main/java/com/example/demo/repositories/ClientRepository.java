package com.example.demo.repositories;

import com.example.demo.entity.ClientData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@RepositoryRestResource
@Repository
public interface ClientRepository  extends JpaRepository<ClientData,Long> {

//    Optional<ClientData>findByName(String name);
}
