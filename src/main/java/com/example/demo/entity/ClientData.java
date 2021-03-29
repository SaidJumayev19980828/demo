package com.example.demo.entity;


import lombok.Getter;


import lombok.RequiredArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
public class ClientData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long Id;



    private String name;
    private String surname;
    private String passportNumber;
    private String date_of_birth;
    private String kafil_person_name;
    private String kafil_passport_number;
    private String kafil_surname;
    private String kafil_date_of_birth;
    private String kafil_income_yearly;


}
