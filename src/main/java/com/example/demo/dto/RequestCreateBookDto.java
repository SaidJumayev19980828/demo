package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RequestCreateBookDto implements Serializable {
    private String phoneNumber="1";
    private String bookName;
    private String bookImage;
    private String bookFile;
}
