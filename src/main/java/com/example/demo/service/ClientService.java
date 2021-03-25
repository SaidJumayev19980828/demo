package com.example.demo.service;


import com.example.demo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public void uploadFile(MultipartFile file) throws IOException {
        file.transferTo(new File("/Users/macbook/Desktop/psql/ "+file.getOriginalFilename()));

    }
    public String saveFileName(MultipartFile file)
    {
        return file.getOriginalFilename();
    }
    public String saveFileDir(MultipartFile file)
    {

       return new File("localhost8078/"+"Desktop/"+file.getOriginalFilename()).toString();

    }


}
