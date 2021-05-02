package com.era.tofate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  RoleDto {
    MANAGER("manager"),
    OPERATOR("operator");

    private final String roleName;
}
