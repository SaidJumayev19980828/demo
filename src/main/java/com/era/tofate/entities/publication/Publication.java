package com.era.tofate.entities.publication;

import com.era.tofate.entities.photo.Photo;
import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.FileMediaType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "publication")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes="id of publication entity")
    private Long id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "avatar_id")
    @ApiModelProperty(notes="entity of Virt")
    private Virt virt;

    @Column(name = "mediaType")
    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(notes="Media type of file")
    private FileMediaType fileMediaType;
    @Column(name = "text_pub", columnDefinition = "TEXT")
    @ApiModelProperty(notes="text of publication")
    private String textPub;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "publication", cascade = CascadeType.ALL)
    @ApiModelProperty(notes="list of publication photos")
    private Set<Photo> photos = new HashSet<>();
    @ApiModelProperty(notes="Date of puplication")
    private LocalDateTime publicationDate = LocalDateTime.now();

    public Publication(Long id) {
        this.id = id;
    }
}
