package com.example.demo.repositories;

import com.example.demo.entity.ClientData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
@Repository
public interface ClientRepository  extends JpaRepository<ClientData,Long> {
 String findByName(String name);
 ClientData findByPassportNumber(String pNumber);
}
