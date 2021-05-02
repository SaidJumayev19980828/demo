package com.era.tofate.payload.photo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoId {
    @ApiModelProperty(notes="id of photo")
    private Long id;
}
