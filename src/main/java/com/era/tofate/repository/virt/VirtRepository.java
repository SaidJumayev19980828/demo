package com.era.tofate.repository.virt;

import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.Sex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface VirtRepository extends JpaRepository<Virt,Long> {
    Page<Virt> findAll(Pageable pageable);
    Page<Virt> findAllBySex(Sex sex, Pageable pageable);

}
