package com.era.tofate.repository.publication;

import com.era.tofate.entities.publication.Publication;
import com.era.tofate.entities.virt.Virt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication,Long> {
    Page<Publication> findAll(Pageable pageable);


}
