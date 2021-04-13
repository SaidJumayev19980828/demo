package com.era.tofate.payload.socket.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthSocketRequest {
    private String accessToken;
}
