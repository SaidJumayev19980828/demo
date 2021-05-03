package com.era.tofate.entities.virt;

import com.era.tofate.entities.publication.Publication;
import com.era.tofate.enums.Sex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "virt")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Virt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "virt_name")
    private String name;
    @Column(name = "avatar_url")
    private String avatar;
    @Column(name = "subscribers_quantity")
    private int subscribersQuantity;
    @Column(name = "subscription_quantity")
    private int subscriptionQuantity;
    @Column(name = "public_post_quantity")
    private int publicPostQuantity;
    @Column(name = "lk_lable_one")
    private String lkLableOne;
    @Column(name = "about")
    private String about;
    @Column(name = "status")
    private String status;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "virt", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Publication> publications;
    @Enumerated(value = EnumType.STRING)
    private Sex sex;
    @Column(name = "age")
    private Long age;

}
