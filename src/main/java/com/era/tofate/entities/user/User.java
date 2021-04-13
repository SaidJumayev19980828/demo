package com.era.tofate.entities.user;

import com.era.tofate.entities.country.Country;
import com.era.tofate.entities.socialstatus.SocialStatus;
import com.era.tofate.entities.userrole.UserRole;
import com.era.tofate.enums.AuthType;
import com.era.tofate.enums.Sex;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<UserRole> roles = new HashSet<>();

    private String login;

    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private int age;

    @Column(name = "about_myself", columnDefinition = "TEXT")
    private String aboutMyself;

    @Enumerated(value = EnumType.STRING)
    private Sex sex;

    @Column(name = "auth_type")
    @Enumerated(value = EnumType.STRING)
    private AuthType authType;

    @ManyToOne
    @JoinColumn(name = "social_status_id")
    @JsonIgnore
    private SocialStatus socialStatus;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonIgnore
    private Country country;

    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "work_time_begin")
    private LocalTime workTimeBegin;

    @Column(name = "work_time_end")
    private LocalTime workTimeEnd;

    @Column(name = "sleep_time_begin")
    private LocalTime sleepTimeBegin;

    @Column(name = "sleep_time_end")
    private LocalTime sleepTimeEnd;

    @Column(name = "weekends_begin")
    private Date weekendsBegin;

    @Column(name = "weekends_end")
    private Date weekendsEnd;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "sms_code")
    @JsonIgnore
    private String smsCode;

    @Column(name = "sms_code_send_date")
    @JsonIgnore
    private Date smsCodeSendDate;

    @Column(name = "sms_code_enter_count")
    @JsonIgnore
    private Long smsCodeEnterCount;

    @Column(name = "date_create")
    @JsonIgnore
    private Date dateCreate;
}
