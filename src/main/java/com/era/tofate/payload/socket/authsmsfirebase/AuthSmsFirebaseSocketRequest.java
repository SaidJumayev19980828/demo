package com.era.tofate.payload.socket.authsmsfirebase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthSmsFirebaseSocketRequest {
    private String idToken;
    private String phoneNumber;
}
