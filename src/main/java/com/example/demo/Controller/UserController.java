package com.example.demo.Controller;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")


public class UserController {

private final UserService userService;



     @PostMapping("details")
    public ResponseEntity<UserDto>getCurrentUserDetails(String principal, @RequestBody UserDto userDto)
     {
         principal=userDto.getPhoneNumber();
         return ResponseEntity.status(HttpStatus.OK).body(userService.getCurrentUserDetails(principal));
     }




}
