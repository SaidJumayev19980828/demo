package com.era.tofate.service.publication;

import com.era.tofate.entities.publication.Publication;
import com.era.tofate.entities.virt.Virt;
import com.era.tofate.service.GeneralService;
import org.springframework.data.domain.Page;

public interface PublicationService extends GeneralService<Publication> {
    Page<Publication> findAll(int page, int pageSize);
}
