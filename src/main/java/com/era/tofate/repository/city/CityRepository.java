package com.era.tofate.repository.city;

import com.era.tofate.entities.city.City;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {
    List<City> findByCountryIdAndTitleRuStartingWith(Long countryId, String titleRu, Pageable pageable);

    List<City> findByCountryIdAndTitleEnStartingWith(Long countryId, String titleRu, Pageable pageable);

    Optional<City> findByCityId(Long id);

    @Query(value = "SELECT max(city_id)+1 FROM _cities WHERE 1 = 1", nativeQuery = true)
    Long getNewCityId();

    @Modifying
    @Query(value = "INSERT INTO `_cities`(`city_id`, `country_id`,`important`, `title_ru`, `title_en`, `region_ru`, `region_en`) " +
            "VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)" ,nativeQuery = true)
    void insertNewCity(Long cityId,Long countryId, Long important, String titleRu, String titleEn, String regionRu, String regionEn);
}
