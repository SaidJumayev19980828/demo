package com.era.tofate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialStatusEnum {
    BUSINESSMAN("businessman"),
    SPORTSMAN("sportsman"),
    STUDENT("student");

    private final String socialStatus;
}
