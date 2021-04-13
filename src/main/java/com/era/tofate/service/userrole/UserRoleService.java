package com.era.tofate.service.userrole;

import com.era.tofate.entities.userrole.UserRole;
import com.era.tofate.service.GeneralService;

import java.util.List;

public interface UserRoleService extends GeneralService<UserRole> {
    List<UserRole> findAllByUser_Login(String login);
}
