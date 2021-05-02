package com.era.tofate.payload.virt;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VirtId {
    @ApiModelProperty(notes="id of virt")
    private Long id;
}
