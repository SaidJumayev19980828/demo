package com.era.tofate.service.city;

import com.era.tofate.entities.city.City;
import com.era.tofate.entities.city.CityFilter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface CityService {
    List<City> findByCountryIdAndTitleRu(Long countryId, String titleRu, int page);

    List<City> findByCountryIdAndTitleEn(Long countryId, String titleEn, int page);

    Optional<City> findByCityId(Long id);

    Stream<City> fetch(int page, int limit, CityFilter citiesFilter);

    Long count(CityFilter citiesFilter);

    Long getNewCityId();

    void insertNewCity(Long cityId, Long countryId, Long important, String titleRu, String titleEn, String regionRu, String regionEn);
}
