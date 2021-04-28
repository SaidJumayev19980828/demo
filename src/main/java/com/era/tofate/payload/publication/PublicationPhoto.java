package com.era.tofate.payload.publication;

import com.era.tofate.entities.photo.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicationPhoto {
    private Long id;
    private Set<Photo> photos = new HashSet<>();

}
