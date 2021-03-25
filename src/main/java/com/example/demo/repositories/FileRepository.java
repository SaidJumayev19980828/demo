package com.example.demo.repositories;

import com.example.demo.entity.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface FileRepository extends JpaRepository<FileModel,Long> {
   public FileModel findByName(String name);
}