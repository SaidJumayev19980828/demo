package com.example.demo.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepositories;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepositories userRepositories;


    @Transactional(readOnly = true)
    @Override
    public UserDto getCurrentUserDetails(String principal) {
        User user=userRepositories.findByPhoneNumber(principal).orElse(null);
        UserDto userDto=new UserDto();
        BeanUtils.copyProperties(user,userDto);
        return userDto;
    }

    @Override
    public boolean createUser(UserDto userDto) {
        return false;
    }
}
