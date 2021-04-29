package com.era.tofate.service.photo;

import com.era.tofate.entities.photo.Photo;
import com.era.tofate.repository.photo.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PhotoServiceImpl implements PhotoService{
    private final PhotoRepository repository;

    @Override
    public Photo getOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Optional<Photo> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Photo save(Photo object) {
        return repository.save(object);
    }

    @Override
    public List<Photo> getAll() {
        return repository.findAll();
    }

    @Override
    public Set<Photo> findAllByPublicationId(Long publicationId) {
        return repository.findAllByPublicationId(publicationId);
    }

    @Override
    public Set<Photo> findAllByPublicationIdAndDeleted(Long publicationId, boolean deleted) {
        return repository.findAllByPublicationIdAndDeleted(publicationId,deleted);
    }

    @Override
    public void deleteById(Long id){
        if (repository.findById(id).isPresent()){
            Photo photo = repository.findById(id).get();
            photo.setDeleted(true);
            repository.save(photo);
        }
    }
}
