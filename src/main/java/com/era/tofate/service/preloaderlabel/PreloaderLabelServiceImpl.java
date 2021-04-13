package com.era.tofate.service.preloaderlabel;

import com.era.tofate.entities.preloaderlabel.PreloaderLabel;
import com.era.tofate.repository.preloaderlabel.PreloaderLabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PreloaderLabelServiceImpl implements PreloaderLabelService {
    private final PreloaderLabelRepository repository;

    @Override
    public PreloaderLabel getOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Optional<PreloaderLabel> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public PreloaderLabel save(PreloaderLabel object) {
        return repository.save(object);
    }

    @Override
    public List<PreloaderLabel> getAll() {
        return repository.findAll();
    }
}
