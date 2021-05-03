package com.era.tofate.payload.virt;

import com.era.tofate.enums.Sex;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VirtRequestByGender {
    @ApiModelProperty(notes="gender of virt")
    private Sex sex;
    @ApiModelProperty(notes="number of page")
    private int page;
    @ApiModelProperty(notes="size of page")
    private int pageSize;
}
