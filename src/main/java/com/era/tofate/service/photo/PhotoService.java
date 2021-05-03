package com.era.tofate.service.photo;

import com.era.tofate.entities.photo.Photo;
import com.era.tofate.service.GeneralService;

import java.util.List;
import java.util.Set;

public interface PhotoService extends GeneralService<Photo> {
    Set<Photo> findAllByPublicationId(Long publicationId);
    Set<Photo> findAllByPublicationIdAndDeleted(Long publicationId,boolean deleted);

    void deleteById(Long id);

}
