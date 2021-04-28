package com.era.tofate.payload.auth;

import com.era.tofate.entities.userrole.UserRole;
import com.era.tofate.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserToken {

    private String login;
    private String token;
    private String tokenType;
    private Role role;
    List<UserRole> roles = new ArrayList<>();

    public UserToken(Role role, String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
        this.role = role;
    }
}
