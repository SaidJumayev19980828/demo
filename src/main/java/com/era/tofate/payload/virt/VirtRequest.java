package com.era.tofate.payload.virt;

import com.era.tofate.entities.publication.Publication;
import com.era.tofate.enums.Sex;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class VirtRequest {
    @ApiModelProperty(notes="id of virt")
    private Long id;
    @ApiModelProperty(notes="name of virt")
    private String name;
    @ApiModelProperty(notes="avatar url of virt")
    private String avatar;
    @ApiModelProperty(notes="quantity of subscribers")
    private int subscribersQuantity;
    @ApiModelProperty(notes="quantity of subscription")
    private int subscriptionQuantity;
    @ApiModelProperty(notes="quantity of public posts")
    private int publicPostQuantity;
    private String lkLableOne;
    @ApiModelProperty(notes="information about virt")
    private String about;
    @ApiModelProperty(notes="status of virt")
    private String status;
    @ApiModelProperty(notes="gender of virt")
    private Sex sex;
    @ApiModelProperty(notes="list of publications")
    private List<Publication> publications;
    @ApiModelProperty(notes="age of virt")
    private Long age;


}
