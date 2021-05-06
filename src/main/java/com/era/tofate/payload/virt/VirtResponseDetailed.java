package com.era.tofate.payload.virt;

import com.era.tofate.entities.publication.Publication;
import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.Sex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
public class VirtResponseDetailed {
    private Long id;
    private String name;
    private String avatar;
    private String about;
    private Sex sex;
    private int subscribersQuantity;
    private int subscriptionQuantity;
    private int publicPostQuantity;
    private String lkLableOne;
    private List<Publication> publications;
    private Long age;

    public VirtResponseDetailed(Virt virt) {

        this.id = virt.getId();
        this.name = virt.getName();
        this.avatar = virt.getAvatar();
        this.about = virt.getAbout();
        this.sex = virt.getSex();
        this.subscribersQuantity = virt.getSubscribersQuantity();
        this.subscriptionQuantity = virt.getSubscriptionQuantity();
        this.publicPostQuantity = virt.getPublicPostQuantity();
        this.lkLableOne = virt.getLkLableOne();
        this.publications = virt.getPublications();
        this.age = virt.getAge();
    }
}
