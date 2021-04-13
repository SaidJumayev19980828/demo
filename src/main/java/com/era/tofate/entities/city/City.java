package com.era.tofate.entities.city;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_cities", indexes = {
        @Index(columnList = "title_ru", name = "title_ru"),
        @Index(columnList = "title_en", name = "title_en")
})
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "title_ru")
    private String titleRu;

    @Column(name = "region_ru")
    private String regionRu;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "region_en")
    private String regionEn;
}
