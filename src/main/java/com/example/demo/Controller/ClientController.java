package com.example.demo.Controller;

import com.example.demo.dto.ClientDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.ClientData;

import com.example.demo.entity.FileModel;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.FileRepository;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientService clientService;




    @Autowired
    FileRepository fileRepository;


    @PostMapping("image")
    public void postUserImage(@RequestParam ("file")MultipartFile file) throws IOException {



        FileModel fileModel=new FileModel(file.getOriginalFilename(),file.getContentType(),file.getBytes());
        fileRepository.save(fileModel);

        System.out.println("FileName"+clientService.saveFileName(file));
        System.out.println(clientService.saveFileDir(file));


    }
    @PostMapping("data")
    public ClientData postUserData(@RequestBody ClientData clientData)
    {

        return clientRepository.save(clientData);
    }


}
