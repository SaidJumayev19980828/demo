package com.example.demo.impl;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.entity.Books;
import com.example.demo.entity.User;
import com.example.demo.entity.UserBooks;
import com.example.demo.repositories.BookRepositories;
import com.example.demo.repositories.UserBooksRepositories;
import com.example.demo.repositories.UserRepositories;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j

public class BookServiceImpl implements BookService {


    private final BookRepositories bookRepositories;
    private final UserRepositories userRepositories;
    private final UserBooksRepositories ucRepository;
    @Transactional(readOnly = true)
    @Override
    public ResponseDto getCurrentBooksDetails(String principal) {
     User user=userRepositories.findByPhoneNumber(principal).orElse(null);

        System.out.println("XXXX"+principal);
//        BookDto bookDto=new BookDto();
//        BeanUtils.copyProperties(books,bookDto);

        List<UserBooks>allByUser=ucRepository.findAllByUser(user);
        List<BookDto>result=allByUser.stream().map(UserBooks::getBooks).map(Books::getDto).collect(Collectors.toList());

        return new ResponseDto(result,false,"OK",HttpStatus.OK);


    }



}
