package com.era.tofate.ws.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ActionType {
    AUTH("auth"),
    AUTHSMSFIREBASE("authsmsfirebase");

    private final String action;

    @JsonCreator
    public static ActionType fromValue(String value) {
        for (ActionType actionType : values()) {
            if (actionType.name().equalsIgnoreCase(value)) {
                return actionType;
            }
        }
        return null;
    }

    @JsonValue
    public String getAction() {
        return action;
    }

}
