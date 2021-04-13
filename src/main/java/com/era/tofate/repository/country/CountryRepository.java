package com.era.tofate.repository.country;

import com.era.tofate.entities.country.Country;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    List<Country> findByTitleRuStartingWith(String title, Pageable pageable);

    List<Country> findByTitleEnStartingWith(String title, Pageable pageable);

    Optional<Country> findByCountryId(Long id);
}
