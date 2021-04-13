package com.era.tofate.service.aboutapp;

import com.era.tofate.entities.aboutapp.AboutApp;
import com.era.tofate.repository.aboutapp.AboutAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AboutAppServiceImpl implements AboutAppService {
    private final AboutAppRepository repository;

    @Override
    public AboutApp getOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Optional<AboutApp> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public AboutApp save(AboutApp object) {
        return repository.save(object);
    }

    @Override
    public List<AboutApp> getAll() {
        return repository.findAll();
    }

    @Override
    public Long count() {
        return repository.count();
    }
}
