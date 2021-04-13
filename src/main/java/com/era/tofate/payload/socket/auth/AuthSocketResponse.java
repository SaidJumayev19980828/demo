package com.era.tofate.payload.socket.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthSocketResponse {
    private String status;
    private Long accountId;
}
