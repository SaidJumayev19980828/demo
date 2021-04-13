package com.era.tofate.service.country;

import com.era.tofate.entities.country.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    List<Country> findByTitleRu(String title, int page);

    List<Country> findByTitleEn(String title, int page);

    Optional<Country> findByCountryId(Long id);
}
