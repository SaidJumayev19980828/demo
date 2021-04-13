package com.era.tofate.payload.firebase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderUserInfoFirebase {
    private String phoneNumber;

    private String providerId;

    private String rawId;

    @Override
    public String toString() {
        return "ProviderUserInfo [phoneNumber = " + phoneNumber + ", providerId = " + providerId + ", rawId = " + rawId + "]";
    }
}
