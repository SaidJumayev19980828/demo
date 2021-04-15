package com.era.tofate.payload.socket.authsmsfirebase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthSmsFirebaseSocketResponse {
    private String status;
    private String statusAuth;
    private String token;
    private Boolean isProfileFilled;

    public AuthSmsFirebaseSocketResponse(String status, String statusAuth) {
        this.status = status;
        this.statusAuth = statusAuth;
    }

    public AuthSmsFirebaseSocketResponse(String status) {
        this.status = status;
    }
}
