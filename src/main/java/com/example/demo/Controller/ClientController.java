package com.example.demo.Controller;

import com.example.demo.dto.ClientDto;
import com.example.demo.entity.ClientData;

import com.example.demo.entity.FileModel;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.FileRepository;
import com.example.demo.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

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
    public ResponseEntity postUserImage(@RequestParam ("file")MultipartFile file) throws IOException {



        FileModel fileModel=new FileModel(file.getOriginalFilename(),file.getContentType(),file.getBytes());
        fileRepository.save(fileModel);

        clientService.uploadFile(file);
        System.out.println("FileName"+clientService.saveFileName(file));
        System.out.println(clientService.saveFileDir(file));

        return ResponseEntity.status(HttpStatus.OK).body("Successfully Uploaded");
    }
    @PostMapping("data")
    public ClientData postUserData(@RequestBody ClientData clientData)
    {

        String pNumber=clientData.getPassportNumber();

        clientRepository.findByPassportNumber(pNumber);
        return clientRepository.save(clientData);
    }
    @PutMapping("/credit/{passportNumber}")
    public void updateClient(@PathVariable(value = "passportNumber") String pNumber,
                                                   @Valid  ClientData clientDetails) throws ResourceNotFoundException {
        ClientData client = clientRepository.findByPassportNumber(pNumber);
//                .orElseThrow(() -> new ResourceNotFoundException("This type of Passport id doesnt exist :: " + pNumber));

//       client.setSurname(clientDetails.getSurname());
//        client.setDate_of_birth(clientDetails.getDate_of_birth());
//

        client.setKafil_person_name(clientDetails.getKafil_person_name());
        client.setKafil_passport_number(clientDetails.getKafil_passport_number());
        client.setKafil_surname(clientDetails.getKafil_surname());
        client.setKafil_income_yearly(clientDetails.getKafil_income_yearly());

        final ClientData updatedClient = clientRepository.save(client);

    }
    @GetMapping("admin")
    public ResponseEntity <List<ClientData>> showAllData(String name)
    {
        return ResponseEntity.status(HttpStatus.OK).body(clientRepository.findAll());
    }



}
