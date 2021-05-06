package com.era.tofate.payload.publication;

import com.era.tofate.entities.photo.Photo;
import com.era.tofate.entities.publication.Publication;
import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.FileMediaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicationResDto {
    @ApiModelProperty(notes="id of publication entity")
    private Long id;
    @ApiModelProperty(notes="id of virt entity")
    private Virt virt;
    @ApiModelProperty(notes="Media type of file")
    private FileMediaType fileMediaType;
    @ApiModelProperty(notes="text of publication")
    private String textPub;
    @ApiModelProperty(notes="list of publication photos")
    private Set<Photo> photos = new HashSet<>();
    @ApiModelProperty(notes="Date of puplication")
    private String publicationDate;

    public PublicationResDto(Publication publication) {
        try {
            this.id = publication.getId();
            this.virt = publication.getVirt();
            this.fileMediaType = publication.getFileMediaType();
            this.textPub = publication.getTextPub();
            this.photos = publication.getPhotos();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            this.publicationDate = publication.getPublicationDate().format(formatter);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
