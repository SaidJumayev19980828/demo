package com.example.demo.entity;

import com.example.demo.MyTables;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = MyTables.WS_HOMEPAGE)
public class HomePage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private String image;
    private String file;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tag")
    @Column(name = "Value")
    private List<String >tags=new ArrayList<>();
    public HomePage(String name,String  description,List<String>tags,String image,String file)
    {
        this.name=name;
        this.description=description;
        this.tags=tags;
        this.file=file;
        this.image=image;
    }
    public HomePage()
    {}

    @Override

    public String toString() {

        return "HomePage{" +

                "id=" + id +

                ", name='" + name + '\'' +

                ", description='" + description + '\'' +

                ", tags=" + tags +

                '}';

    }
}
