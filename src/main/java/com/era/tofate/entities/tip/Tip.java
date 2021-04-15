package com.era.tofate.entities.tip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tips")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "tip_text", columnDefinition = "TEXT")
    private String tipText;

    @Column(name = "sequence_number")
    private int sequenceNumber;
}
