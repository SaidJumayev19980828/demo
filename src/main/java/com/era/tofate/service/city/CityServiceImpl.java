package com.era.tofate.service.city;

import com.era.tofate.entities.city.City;
import com.era.tofate.entities.city.CityFilter;
import com.era.tofate.repository.city.CityRepository;
import com.era.tofate.repository.city.specification.CityCountryIdSpecification;
import com.era.tofate.repository.city.specification.CityTitleEnSpecification;
import com.era.tofate.repository.city.specification.CityTitleRuSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CityServiceImpl implements CityService {
    private final CityRepository repository;

    @Override
    public List<City> findByCountryIdAndTitleRu(Long countryId, String titleRu, int page) {
        return repository.findByCountryIdAndTitleRuStartingWith(countryId, titleRu, PageRequest.of(page, 25));
    }

    @Override
    public List<City> findByCountryIdAndTitleEn(Long countryId, String titleEn, int page) {
        return repository.findByCountryIdAndTitleEnStartingWith(countryId, titleEn, PageRequest.of(page, 25));
    }

    @Override
    public Optional<City> findByCityId(Long id) {
        return repository.findByCityId(id);
    }

    @Override
    public Stream<City> fetch(int page, int limit, CityFilter citiesFilter) {
        Specification<City> specification =
                Specification.where(new CityTitleRuSpecification(citiesFilter.getTitleRu()))
                        .and(new CityTitleEnSpecification(citiesFilter.getTitleEn()))
                        .and(new CityCountryIdSpecification(citiesFilter.getCountryId()));
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "titleRu"));
        return repository.findAll(specification, pageable).stream();
    }

    @Override
    public Long count(CityFilter citiesFilter) {
        Specification<City> specification =
                Specification.where(new CityTitleRuSpecification(citiesFilter.getTitleRu()))
                        .and(new CityTitleEnSpecification(citiesFilter.getTitleEn()))
                        .and(new CityCountryIdSpecification(citiesFilter.getCountryId()));

        return repository.count(specification);
    }

    @Override
    public Long getNewCityId() {
        return repository.getNewCityId();
    }

    @Override
    public void insertNewCity(Long cityId, Long countryId, Long important, String titleRu, String titleEn,
                              String regionRu, String regionEn) {
        repository.insertNewCity(cityId, countryId, important, titleRu, titleEn, regionRu, regionEn );
    }
}
