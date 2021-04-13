package com.era.tofate.payload.firebase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersFirebase {
    private String createdAt;

    private String lastLoginAt;

    private String phoneNumber;

    private ProviderUserInfoFirebase[] providerUserInfo;

    private String localId;

    private String lastRefreshAt;

    @Override
    public String toString() {
        return "Users [createdAt = " + createdAt + ", lastLoginAt = " + lastLoginAt + ", phoneNumber = " + phoneNumber + ", providerUserInfo = " + providerUserInfo + ", localId = " + localId + ", lastRefreshAt = " + lastRefreshAt + "]";
    }
}
