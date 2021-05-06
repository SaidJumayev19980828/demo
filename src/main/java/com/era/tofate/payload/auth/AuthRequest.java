package com.era.tofate.payload.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    @ApiModelProperty(notes="login of account")
    private String login;
    @ApiModelProperty(notes="password of account")
    private String password;
}
