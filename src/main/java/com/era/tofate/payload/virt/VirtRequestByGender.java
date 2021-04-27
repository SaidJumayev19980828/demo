package com.era.tofate.payload.virt;

import com.era.tofate.enums.Sex;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VirtRequestByGender {
    private Sex sex;
    private int page;
    private int pageSize;
}
