package com.era.tofate.payload.user;

import com.era.tofate.entities.user.User;
import com.era.tofate.entities.userrole.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
public class UserResponseDto {
    @ApiModelProperty(notes="id of user")
    private long id;
    @ApiModelProperty(notes="login of account")
    private String login;
    @ApiModelProperty(notes="password of account")
    private String password;
    @ApiModelProperty(notes = "role of user")
    private Set<UserRole> roles = new HashSet<>();

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = "";
        this.roles = user.getRoles();
    }
}
