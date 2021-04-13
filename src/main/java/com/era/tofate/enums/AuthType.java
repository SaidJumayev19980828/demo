package com.era.tofate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthType {
    SMS("sms"),
    EMAIL("email");

    private final String authType;
}
