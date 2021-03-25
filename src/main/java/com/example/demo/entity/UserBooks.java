package com.example.demo.entity;

import com.example.demo.MyTables;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name = MyTables.WS_USER_BOOKS)

public class  UserBooks implements Serializable {

    @Id
//    @GeneratedValue(generator = "optimized-sequence")
    private Long id;

//    public UserBooks( Books books,User user)
//    {
//        this.books=books;
//        this.user=user;
//    }
//

    @ManyToOne
    @JoinColumn(name = "book_name")
    Books books;

    @ManyToOne
    @JoinColumn(name = "phone_number")
    User user;


}
