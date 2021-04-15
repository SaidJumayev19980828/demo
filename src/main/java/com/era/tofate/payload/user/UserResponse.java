package com.era.tofate.payload.user;

import com.era.tofate.entities.socialstatus.SocialStatus;
import com.era.tofate.entities.user.User;
import com.era.tofate.enums.Sex;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalTime;

@Getter
@Setter
public class UserResponse {

    String login;
    String firstName;
    String lastName;
    Long age;
    String aboutMyself;
    @Enumerated(value = EnumType.STRING)
    Sex sex;
    SocialStatus socialStatus;
    String timeZone;
    @JsonFormat(pattern="HH.mm")
    LocalTime workTimeBegin;
    @JsonFormat(pattern="HH.mm")
    LocalTime workTimeEnd;
    @JsonFormat(pattern="HH.mm")
    LocalTime sleepTimeBegin;
    @JsonFormat(pattern="HH.mm")
    LocalTime sleepTimeEnd;
    List<Boolean> weekends = new ArrayList<>();

    public UserResponse(User user) {
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.age = user.getAge();
        this.aboutMyself = user.getAboutMyself();
        this.sex = user.getSex();
        this.socialStatus = user.getSocialStatus();
        this.timeZone = user.getTimeZone();
        this.workTimeBegin = user.getWorkTimeBegin();
        this.workTimeEnd = user.getWorkTimeEnd();
        this.sleepTimeBegin = user.getSleepTimeBegin();
        this.sleepTimeEnd = user.getSleepTimeEnd();
        if (user.getWeekends() != null) {
            String[] arr = user.getWeekends().split(",");
            for (String anArr : arr) {
                weekends.add(Boolean.valueOf(anArr));
            }
        }
    }
}
