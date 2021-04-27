package com.era.tofate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileMediaType {
    PIC("pic"),
    VIDEO("video");

    private final String mediaType;
}
