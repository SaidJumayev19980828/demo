package com.example.demo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;



public class ClientDto {

    private Long Id;
//    @GeneratorType("generated-sequence")

    private String date_of_birth;
    private String name;
    private String surname;
    private String passport_number;
    private String place_of_birth;

    public ClientDto(Long id, String date_of_birth, String name, String surname, String passport_number, String place_of_birth) {
        Id = id;
        this.date_of_birth = date_of_birth;
        this.name = name;
        this.surname = surname;
        this.passport_number = passport_number;
        this.place_of_birth = place_of_birth;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassport_number() {
        return passport_number;
    }

    public void setPassport_number(String passport_number) {
        this.passport_number = passport_number;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }
}
