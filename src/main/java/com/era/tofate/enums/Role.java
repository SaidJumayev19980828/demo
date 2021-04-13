package com.era.tofate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("user"),
    ROOTADMIN("rootadmin");

    private final String roleName;
}
