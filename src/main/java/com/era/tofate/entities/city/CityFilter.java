package com.era.tofate.entities.city;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityFilter {
    String titleRu;
    String titleEn;
    Long countryId;
}
