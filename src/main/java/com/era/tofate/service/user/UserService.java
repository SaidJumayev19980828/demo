package com.era.tofate.service.user;

import com.era.tofate.entities.user.User;
import com.era.tofate.service.GeneralService;

import java.util.Optional;

public interface UserService extends GeneralService<User> {
    Optional<User> findByLogin(String login);
}
