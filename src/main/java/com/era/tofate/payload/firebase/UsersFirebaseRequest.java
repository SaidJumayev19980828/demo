package com.era.tofate.payload.firebase;

import com.era.tofate.payload.socket.authsmsfirebase.AuthSmsFirebaseSocketRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersFirebaseRequest {
    String idToken;

    public UsersFirebaseRequest(AuthSmsFirebaseSocketRequest request) {
        this.idToken = request.getIdToken();
    }
}
