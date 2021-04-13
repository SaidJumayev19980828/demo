package com.era.tofate.service.userrole;

import com.era.tofate.entities.userrole.UserRole;
import com.era.tofate.repository.userrole.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository repository;

    @Override
    public List<UserRole> findAllByUser_Login(String login) {
        return repository.findAllByUser_Login(login);
    }

    @Override
    public UserRole getOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public Optional<UserRole> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public UserRole save(UserRole object) {
        return repository.save(object);
    }

    @Override
    public List<UserRole> getAll() {
        return repository.findAll();
    }
}
