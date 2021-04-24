package com.era.tofate.payload.virt;

import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.Sex;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class VirtResponse {
    private Long id;
    private String name;
    private String avatar;
    private String about;
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
