package com.example.demo.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.server.core.SpringAffordanceBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ClientData implements Serializable {

    @Id
    private Long Id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String date_of_birth;
    private String name;
    private String surname;
    private String passport_number;
    private String place_of_birth;


}
