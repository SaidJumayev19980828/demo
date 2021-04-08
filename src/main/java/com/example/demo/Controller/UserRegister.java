package com.example.demo.Controller;

import com.example.demo.dto.ClientDto;
import com.example.demo.entity.ClientData;
import com.example.demo.repositories.ClientRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reg")

public class UserRegister {

    @Autowired
    ClientRepository clientRepository;


    @PostMapping("details")
    public ClientData userData(ClientData clientData) {


        return clientRepository.save(clientData);
    }

    @GetMapping("checkUser")
    public ResponseEntity<ClientData> loginUser(ClientDto clientDto, ClientData clientData) {

        clientDto.setName(clientData.getName());
        clientDto.setPassport_number(clientData.getPassportNumber());
        String name = clientData.getName();
        String pNumber = clientData.getPassportNumber();

        System.out.println("Passport" + pNumber);
        System.out.println("Name" + name);


        System.out.println("Test" + clientDto.getPassport_number());
        if (clientDto.getName().equals(clientRepository.findByName(name)) && clientDto.getPassport_number().equals(clientRepository.findByPassportNumber(pNumber))) {


            return ResponseEntity.status(HttpStatus.OK).body(clientData);

        } else {
                    return ResponseEntity.status(HttpStatus.OK).body(clientData);
        }


    }
}
