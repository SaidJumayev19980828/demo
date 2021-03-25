package com.example.demo.Controller;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.entity.HomePage;
import com.example.demo.repositories.HomePageBooks;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor

public class BooksController {

    @Autowired
    private HomePageBooks homePageBooks;

@Autowired
private BookService bookService;

    @PostMapping("details")
    public ResponseEntity<ResponseDto>getCurrentBooksDetails(@RequestBody BookDto bookDto, String principal)
    {
        ResponseDto responseDto=bookService.getCurrentBooksDetails("Harry Potter");
        return ResponseEntity.status(responseDto.getStatus()).body(responseDto);

    }
    @GetMapping("all")
    public ResponseEntity<Collection<HomePage>>getAllBooks()
    {
        return new ResponseEntity<>(homePageBooks.findAll(),HttpStatus.OK);
    }



}
