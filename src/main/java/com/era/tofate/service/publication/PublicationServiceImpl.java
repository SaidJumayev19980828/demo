package com.era.tofate.service.publication;

import com.era.tofate.entities.publication.Publication;
import com.era.tofate.repository.publication.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublicationServiceImpl implements PublicationService {
    private final PublicationRepository repository;

    @Override
    public Page<Publication> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return repository.findAll(pageable);
    }
    @Override
    public Publication getOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Optional<Publication> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Publication save(Publication publication) {
        return repository.saveAndFlush(publication);
        /*
        Set<Photo> photoSet = publication.getPhotos();

        publication.setPhotos(new HashSet<>());
        Publication savedPub = repository.saveAndFlush(publication);
        Set<Photo> savedPhotoSet = publication.getPhotos();

        photoSet.forEach(photo -> {
            if (photo.getId() != null){
                if (photoService.findById(photo.getId()).isPresent()){
                    photo = photoService.findById(photo.getId()).get();
                    photo.setPublication(savedPub);
                    Photo savedPhoto = photoService.save(photo);
                    savedPhotoSet.add(savedPhoto);
                }
            }
        });
        savedPub.setPhotos(photoService.findAllByPublicationId(publication.getId()));
        return savedPub;

         */
    }

    @Override
    public List<Publication> getAll() {
        return repository.findAll();
    }
}
