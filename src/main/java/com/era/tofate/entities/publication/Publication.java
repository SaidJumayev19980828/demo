package com.era.tofate.entities.publication;

import com.era.tofate.entities.photo.Photo;
import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.FileMediaType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
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
    @JsonIgnore
    private Long id;
    @ManyToOne
    @JoinColumn(name = "avatar_id")
    private Virt virt;

    @Column(name = "mediaType")
    @Enumerated(value = EnumType.STRING)
    private FileMediaType fileMediaType;
    @Column(name = "text_pub", columnDefinition = "TEXT")
    private String textPub;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "publication", cascade = CascadeType.ALL)
    private Set<Photo> photos = new HashSet<>();
}
