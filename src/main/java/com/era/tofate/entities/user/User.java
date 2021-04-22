package com.era.tofate.entities.user;

import com.era.tofate.entities.country.Country;
import com.era.tofate.entities.socialstatus.SocialStatus;
import com.era.tofate.entities.userrole.UserRole;
import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.AuthType;
import com.era.tofate.enums.Sex;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<UserRole> roles = new HashSet<>();

    private String login;

    @JsonIgnore
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private Long age;

    @Column(name = "about_myself", columnDefinition = "TEXT")
    private String aboutMyself;

    @Enumerated(value = EnumType.STRING)
    private Sex sex;

    @Column(name = "auth_type")
    @Enumerated(value = EnumType.STRING)
    @JsonIgnore
    private AuthType authType;

    @ManyToOne
    @JoinColumn(name = "social_status_id")
    private SocialStatus socialStatus;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonIgnore
    private Country country;

    @ManyToOne
    @JoinColumn(name = "virt_id")
    @JsonIgnore
    private Virt virt;

    @JoinColumn(name = "country_str")
    @JsonIgnore
    private String countryStr;

    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "work_time_begin")
    @JsonFormat(pattern="HH.mm")
    private LocalTime workTimeBegin;

    @Column(name = "work_time_end")
    @JsonFormat(pattern="HH.mm")
    private LocalTime workTimeEnd;

    @Column(name = "sleep_time_begin")
    @JsonFormat(pattern="HH.mm")
    private LocalTime sleepTimeBegin;

    @Column(name = "sleep_time_end")
    @JsonFormat(pattern="HH.mm")
    private LocalTime sleepTimeEnd;

    @Column(name = "weekends")
    private String weekends;

    @Column(name = "enabled")
    @JsonIgnore
    private boolean enabled;

    @Column(name = "date_create")
    @JsonIgnore
    private Date dateCreate;
}
