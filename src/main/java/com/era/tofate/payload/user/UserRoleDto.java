package com.era.tofate.payload.user;

import com.era.tofate.entities.userrole.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserRoleDto {
    @ApiModelProperty(notes="login of account")
    private String login;
    @ApiModelProperty(notes="password of account")
    private String password;
    @ApiModelProperty(notes = "role of user")
    private Set<UserRole> roles = new HashSet<>();
}
