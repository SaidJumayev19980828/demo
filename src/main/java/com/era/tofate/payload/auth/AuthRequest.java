package com.era.tofate.payload.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class AuthRequest {
    @ApiModelProperty(notes="login of account")
    private String login;
    @ApiModelProperty(notes="password of account")
    private String password;
}
