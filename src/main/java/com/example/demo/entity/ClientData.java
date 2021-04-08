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
    private String kafil_ish_haqi_yillik;
    private String kafil_ism_familya;
    private String ish_haqi;
    private String kredit_summasi;


}
