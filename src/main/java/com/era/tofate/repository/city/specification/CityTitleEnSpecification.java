package com.era.tofate.repository.city.specification;

import com.era.tofate.entities.city.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityTitleEnSpecification implements Specification<City> {
    String titleEn;

    @Override
    public Predicate toPredicate(Root<City> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (titleEn == null) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }

        if (titleEn.equals("")) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }

        return criteriaBuilder.like(root.get("titleEn"), titleEn+"%");
    }
}
