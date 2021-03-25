package com.example.demo.service;

import com.example.demo.dto.UserDto;

public interface UserService  {
    UserDto getCurrentUserDetails(String principal);
    boolean createUser(UserDto userDto);
}
