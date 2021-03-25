package com.example.demo.entity;

import com.example.demo.MyTables;
import com.example.demo.dto.BookDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = MyTables.WS_BOOKS)
public class Books implements Serializable {
    @Id
//    @GeneratedValue(generator = "optimized-sequence")
    private Long id;

    @Column(nullable = false)
    private String bookName;
    private String bookImage;
    private String bookFile;


    public BookDto getDto()
    {
        BookDto bookDto=new BookDto();
        BeanUtils.copyProperties(this,bookDto,"bookName","bookImage","bookFile");
        return bookDto;

    }



}
