package com.era.tofate.payload.virt;

import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.Sex;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class VirtResponse {
    @ApiModelProperty(notes="id of virt")
    private Long id;
    @ApiModelProperty(notes="name of virt")
    private String name;
    @ApiModelProperty(notes="avatar url of virt")
    private String avatar;
    @ApiModelProperty(notes="information about virt")
    private String about;
    @ApiModelProperty(notes="gender of virt")
    private Sex sex;

    public VirtResponse(Long id, Sex sex) {
        this.id = id;
        this.sex = sex;
    }
    public VirtResponse(Virt virt) {
        this.id = virt.getId();
        this.sex = virt.getSex();
        this.avatar = virt.getAvatar();
        this.about = virt.getAbout();
        this.sex = virt.getSex();
        this.name = virt.getName();
    }
}
