package com.era.tofate.service.socialstatus;

import com.era.tofate.entities.socialstatus.SocialStatus;
import com.era.tofate.repository.socialstatus.SocialStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SocialStatusServiceImpl implements SocialStatusService {
    private final SocialStatusRepository repository;

    @Override
    public SocialStatus getOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Optional<SocialStatus> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public SocialStatus save(SocialStatus object) {
        return repository.save(object);
    }

    @Override
    public List<SocialStatus> getAll() {
        return repository.findAll();
    }
}
