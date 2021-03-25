package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class BookDto extends RequestCreateBookDto implements Serializable {
    private String bookName;
    private String bookImage;
    private String bookFile;
}
