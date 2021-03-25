package com.example.demo.service;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.ResponseDto;

public interface BookService {
    ResponseDto getCurrentBooksDetails(String principal);
}
