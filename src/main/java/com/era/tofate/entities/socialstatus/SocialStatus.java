package com.era.tofate.entities.socialstatus;

import com.era.tofate.enums.SocialStatusEnum;
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
@Table(name = "social_status")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SocialStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mime_type")
    @JsonIgnore
    private String mimeType;

    @Lob
    @JsonIgnore
    private byte[] image;

    @Column(name = "path")
    private String url;

    @Column(name = "social_status")
    @Enumerated(value = EnumType.STRING)
    private SocialStatusEnum socialStatusEnum;
}
