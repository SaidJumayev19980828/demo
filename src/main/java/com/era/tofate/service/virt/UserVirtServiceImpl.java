package com.era.tofate.service.virt;

import com.era.tofate.entities.virt.UserVirt;
import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.Sex;
import com.era.tofate.repository.virt.UserVirtRepository;
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
public class UserVirtServiceImpl implements UserVirtService{
    private final UserVirtRepository repository;

    @Override
    public UserVirt getOne(Long id) {
        return null;
    }

    @Override
    public Optional<UserVirt> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public UserVirt save(UserVirt object) {
        return null;
    }

    @Override
    public List<UserVirt> getAll() {
        return null;
    }
}
