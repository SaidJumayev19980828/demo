package com.era.tofate.payload.publication;

import com.era.tofate.enums.FileMediaType;
import com.era.tofate.payload.photo.PhotoId;
import com.era.tofate.payload.virt.VirtId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDto {
    @ApiModelProperty(notes="id of publication entity")
    private Long id;
    @ApiModelProperty(notes="id of virt entity")
    private VirtId virt;
    @ApiModelProperty(notes="Media type of file")
    private FileMediaType fileMediaType;
    @ApiModelProperty(notes="text of publication")
    private String textPub;
    @ApiModelProperty(notes="list of publication photos")
    private Set<PhotoId> photos = new HashSet<>();
}
