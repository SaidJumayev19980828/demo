package com.era.tofate.payload.firebase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersFirebaseResponse {
    private String kind;

    private UsersFirebase[] users;

    @Override
    public String toString() {
        return "UsersFirebaseResponse [kind = " + kind + ", users = " + users + "]";
    }
}
