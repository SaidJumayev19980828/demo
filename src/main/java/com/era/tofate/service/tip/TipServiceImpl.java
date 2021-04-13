package com.era.tofate.service.tip;

import com.era.tofate.entities.tip.Tip;
import com.era.tofate.repository.tip.TipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TipServiceImpl implements TipService {
    private final TipRepository repository;

    @Override
    public Tip getOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Optional<Tip> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Tip save(Tip object) {
        return repository.save(object);
    }

    @Override
    public List<Tip> getAll() {
        return repository.findAll();
    }
}
