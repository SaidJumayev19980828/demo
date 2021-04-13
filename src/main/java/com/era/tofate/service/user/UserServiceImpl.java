package com.era.tofate.service.user;

import com.era.tofate.entities.user.User;
import com.era.tofate.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public User getOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public User save(User object) {
        return repository.save(object);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }
}
