package com.era.tofate.repository.photo;

import com.era.tofate.entities.photo.Photo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long> {
    Set<Photo> findAllByPublicationId(Long publicationId);
    Set<Photo> findAllByPublicationIdAndDeleted(Long publicationId,boolean deleted);

    @Transactional
    void deleteById(Long id);
    @Transactional
    void removeById(Long id);
}
