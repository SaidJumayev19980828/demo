package com.era.tofate.service.virt;

import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.Sex;
import com.era.tofate.service.GeneralService;
import org.springframework.data.domain.Page;


public interface VirtService extends GeneralService<Virt> {
    Page<Virt> findAll(int page,int pageSize);
    Page<Virt> findAllBySex(Sex sex, int page, int pageSize);
}
