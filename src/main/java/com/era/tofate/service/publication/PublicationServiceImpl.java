package com.era.tofate.service.publication;

import com.era.tofate.entities.publication.Publication;
import com.era.tofate.entities.virt.Virt;
import com.era.tofate.repository.publication.PublicationRepository;
import com.era.tofate.repository.user.UserRepository;
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
