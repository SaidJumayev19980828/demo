package com.era.tofate.repository.faq;

import com.era.tofate.entities.faq.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FAQRepository extends JpaRepository<FAQ,Long> {
    Optional<FAQ>findById(Long id);
}
