package com.era.tofate.service.country;

import com.era.tofate.entities.country.Country;
import com.era.tofate.repository.country.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CountryServiceImpl implements CountryService {
    private final CountryRepository repository;

    @Override
    public List<Country> findByTitleRu(String title, int page) {
        return repository.findByTitleRuStartingWith(title, PageRequest.of(page, 25));
    }

    @Override
    public List<Country> findByTitleEn(String title, int page) {
        return repository.findByTitleEnStartingWith(title, PageRequest.of(page, 25));
    }

    @Override
    public Optional<Country> findByCountryId(Long id) {
        return repository.findByCountryId(id);
    }
}
