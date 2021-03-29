package com.example.demo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;


public class ClientDto implements Serializable {

    private Long Id;


    private String date_of_birth;
    private String name;
    private String surname;
    private String passport_number;
    private String place_of_birth;
    private String kafil_person_name;
    private String kafil_passport_number;
    private String kafil_surname;
    private String kafil_place_of_birth;
    private String kafil_income_yearly;

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

    public String getKafil_person_name() {
        return kafil_person_name;
    }

    public void setKafil_person_name(String kafil_person_name) {
        this.kafil_person_name = kafil_person_name;
    }

    public String getKafil_passport_number() {
        return kafil_passport_number;
    }

    public void setKafil_passport_number(String kafil_passport_number) {
        this.kafil_passport_number = kafil_passport_number;
    }

    public String getKafil_surname() {
        return kafil_surname;
    }

    public void setKafil_surname(String kafil_surname) {
        this.kafil_surname = kafil_surname;
    }

    public String getKafil_place_of_birth() {
        return kafil_place_of_birth;
    }

    public void setKafil_place_of_birth(String kafil_place_of_birth) {
        this.kafil_place_of_birth = kafil_place_of_birth;
    }

    public String getKafil_income_yearly() {
        return kafil_income_yearly;
    }

    public void setKafil_income_yearly(String kafil_income_yearly) {
        this.kafil_income_yearly = kafil_income_yearly;
    }
}
