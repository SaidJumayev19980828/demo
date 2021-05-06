package com.era.tofate.service.user;

import com.era.tofate.entities.user.User;
import com.era.tofate.enums.Role;
import com.era.tofate.enums.RoleDto;
import com.era.tofate.payload.auth.UserToken;
import com.era.tofate.service.GeneralService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService extends GeneralService<User> {
    Optional<User> findByLogin(String login);
    UserToken auth(String login, String password);
    List<Map<String,Object>> findAllByRolesContaining(RoleDto role, int page, int pageSize);

}
