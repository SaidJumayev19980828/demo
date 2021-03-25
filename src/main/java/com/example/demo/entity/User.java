package com.example.demo.entity;

import com.example.demo.MyTables;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = MyTables.WS_USERS)
public class User implements Serializable {
    @Id
//    @GeneratedValue(generator = "optimized-sequence")
    private Long id;

    @Column (nullable = false)
    private String userName;
    private String phoneNumber;

public User(Long id)
{
    this.id=id;
}

}
