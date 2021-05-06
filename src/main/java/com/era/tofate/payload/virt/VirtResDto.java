package com.era.tofate.payload.virt;

import com.era.tofate.entities.publication.Publication;
import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.Sex;
import com.era.tofate.payload.publication.PublicationDto;
import com.era.tofate.payload.publication.PublicationResDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VirtResDto {
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

    private List<PublicationResDto> publications;
    @Enumerated(value = EnumType.STRING)
    private Sex sex;
    @Column(name = "age")
    private Long age;

    public VirtResDto(Virt virt){
        this.id = virt.getId();
        this.name = virt.getName();
        this.avatar = virt.getAvatar();
        this.subscribersQuantity = virt.getSubscribersQuantity();
        this.subscriptionQuantity = virt.getSubscriptionQuantity();
        this.publicPostQuantity = virt.getPublicPostQuantity();
        this.lkLableOne = virt.getLkLableOne();
        this.about = virt.getAbout();
        this.status = virt.getStatus();
        this.sex = virt.getSex();
        this.age = virt.getAge();
        this.publications = new ArrayList<>();
        virt.getPublications().forEach(publication -> {
            publications.add(new PublicationResDto(publication));
        });
    }
}
