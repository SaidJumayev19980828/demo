package com.era.tofate.payload.virt;

import com.era.tofate.entities.publication.Publication;
import com.era.tofate.enums.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class VirtResponse {
    private Long id;
    private String name;
    private String avatar;
    private int subscribersQuantity;
    private int subscriptionQuantity;
    private int publicPostQuantity;
    private String lkLableOne;
    private String about;
    private String status;
    private Sex sex;
    private List<Publication> publications;

    public VirtResponse(Long id, Sex sex) {
        this.id = id;
        this.sex = sex;
    }
}
