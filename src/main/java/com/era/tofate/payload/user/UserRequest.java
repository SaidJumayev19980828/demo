package com.era.tofate.payload.user;

import com.era.tofate.enums.Sex;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class UserRequest {
    private String firstName;
    private String lastName;
    private String countryStr;
    private Long age;
    private Long socialStatusId;
    @Enumerated(value = EnumType.STRING)
    private Sex sex;
    private String aboutMyself;
    private String timeZone;
    private String workTimeBegin;
    private String workTimeEnd;
    private String sleepTimeBegin;
    private String sleepTimeEnd;
    private List<Boolean> weekends;
}
