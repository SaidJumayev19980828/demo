package com.era.tofate.service.publication;

import com.era.tofate.entities.publication.Publication;
import com.era.tofate.repository.publication.PublicationRepository;
import com.era.tofate.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublicationServiceImpl implements PublicationService {
    private final PublicationRepository repository;

    @Override
    public Publication getOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Optional<Publication> findById(Long id) {
        return findById(id);
    }

    @Override
    public Publication save(Publication object) {
        return repository.save(object);
    }

    @Override
    public List<Publication> getAll() {
        return repository.findAll();
    }
}
