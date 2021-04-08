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

        client.setKafil_ism_familya(clientDetails.getKafil_ism_familya());
        client.setIsh_haqi(clientDetails.getIsh_haqi());
        client.setKafil_ish_haqi_yillik(clientDetails.getKafil_ish_haqi_yillik());
        client.setKredit_summasi(clientDetails.getKredit_summasi());

        final ClientData updatedClient = clientRepository.save(client);

    }
    @GetMapping("admin")
    public ResponseEntity <List<ClientData>> showAllData(String name)
    {
        return ResponseEntity.status(HttpStatus.OK).body(clientRepository.findAll());
    }



}
