package com.era.tofate.payload.auth;

import com.era.tofate.entities.userrole.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserToken {
    @ApiModelProperty(notes="login of account")
    private String login;
    @ApiModelProperty(notes="token after authentication")
    private String token;
    @ApiModelProperty(notes="type of token (default: bearer)")
    private String tokenType;
    @ApiModelProperty(notes="list of roles")
    List<UserRole> roles = new ArrayList<>();

    public UserToken(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }
}
