package com.era.tofate.service.virt;

import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.Sex;
import com.era.tofate.repository.virt.VirtRepository;
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
public class VirtServiceImpl implements VirtService{
    private final VirtRepository repository;

    @Override
    public Virt getOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Optional<Virt> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Virt save(Virt object) {
        return repository.save(object);
    }

    @Override
    public List<Virt> getAll() {
        return repository.findAll();
    }

    @Override
    public Page<Virt> findAll(int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        return repository.findAll(pageable);
    }
    @Override
    public Page<Virt> findAllBySex(Sex sex, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return repository.findAllBySex(sex,pageable);
    }
}
