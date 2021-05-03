package com.era.tofate.controller.user;

import com.era.tofate.entities.socialstatus.SocialStatus;
import com.era.tofate.entities.user.User;
import com.era.tofate.enums.Sex;
import com.era.tofate.exceptions.BadRequestException;
import com.era.tofate.payload.user.UserRequest;
import com.era.tofate.payload.user.UserResponse;
import com.era.tofate.security.CurrentUser;
import com.era.tofate.security.UserPrincipal;
import com.era.tofate.service.socialstatus.SocialStatusService;
import com.era.tofate.service.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Optional;

import static com.era.tofate.exceptions.ExceptionConstants.NO_ACCESS;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;
    private final SocialStatusService socialStatusService;
    private final PasswordEncoder passwordEncoder;


    /**
     * Updating user information
     *
     * @param userPrincipal - authorized user
     * @param userRequest - User Entity
     * @return User Entity
     */
    @PostMapping("/api/user/account")
    public ResponseEntity<?> updateAccount(@CurrentUser UserPrincipal userPrincipal, @RequestBody UserRequest userRequest) {
        if (userService.findById(userPrincipal.getId()).isPresent()) {

            User currentUser = userService.findById(userPrincipal.getId()).get();

            Optional<String>        firstName = Optional.ofNullable(userRequest.getFirstName());
            Optional<String>        lastName = Optional.ofNullable(userRequest.getLastName());
            Optional<String>        countryStr = Optional.ofNullable(userRequest.getCountryStr());
            Optional<Long>          age = Optional.ofNullable(userRequest.getAge());
            Optional<Long>          socialStatusId = Optional.ofNullable(userRequest.getSocialStatusId());
            Optional<Sex>           sex = Optional.ofNullable(userRequest.getSex());
            Optional<String>        aboutMyself = Optional.ofNullable(userRequest.getAboutMyself());
            Optional<String>        timeZone = Optional.ofNullable(userRequest.getTimeZone());

            Optional<LocalTime>     workTimeBegin = Optional.empty();
            if (userRequest.getWorkTimeBegin() != null) {
                workTimeBegin = Optional.ofNullable(LocalTime.parse(userRequest.getWorkTimeBegin()));
            }

            Optional<LocalTime>     workTimeEnd = Optional.empty();;
            if (userRequest.getWorkTimeEnd() != null) {
                workTimeEnd = Optional.ofNullable(LocalTime.parse(userRequest.getWorkTimeEnd()));
            }

            Optional<LocalTime>     sleepTimeBegin = Optional.empty();;
            if (userRequest.getSleepTimeBegin() != null) {
                sleepTimeBegin = Optional.ofNullable(LocalTime.parse(userRequest.getSleepTimeBegin()));
            }

            Optional<LocalTime>     sleepTimeEnd = Optional.empty();;
            if (userRequest.getSleepTimeEnd() != null) {
                sleepTimeEnd = Optional.ofNullable(LocalTime.parse(userRequest.getSleepTimeEnd()));
            }

            Optional<List<Boolean>> weekends = Optional.ofNullable(userRequest.getWeekends());

            weekends.ifPresent(items -> {
                currentUser.setWeekends(getWeekendsStr(items));
            });

            firstName.ifPresent(currentUser::setFirstName);
            lastName.ifPresent(currentUser::setLastName);
            countryStr.ifPresent(currentUser::setCountryStr);
            age.ifPresent(currentUser::setAge);
            socialStatusId.ifPresent(statusId -> {
                SocialStatus socialStatus = socialStatusService.getOne(statusId);
                currentUser.setSocialStatus(socialStatus);
            });
            sex.ifPresent(currentUser::setSex);
            aboutMyself.ifPresent(currentUser::setAboutMyself);
            timeZone.ifPresent(currentUser::setTimeZone);
            workTimeBegin.ifPresent(currentUser::setWorkTimeBegin);
            workTimeEnd.ifPresent(currentUser::setWorkTimeEnd);
            sleepTimeBegin.ifPresent(currentUser::setSleepTimeBegin);
            sleepTimeEnd.ifPresent(currentUser::setSleepTimeEnd);

            User resultUser = userService.save(currentUser);

            return ResponseEntity.ok(new UserResponse(resultUser));
        } else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    /**
     *
     * @param userPrincipal - authorized user
     * @return User Entity
     */
    @GetMapping("/api/user/account")
    public ResponseEntity<?> updateAccount(@CurrentUser UserPrincipal userPrincipal) {
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            return ResponseEntity.ok(new UserResponse(userService.findById(userPrincipal.getId()).get()));
        } else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    /**
     * Update user password (for test only)
     *
     * @param userPrincipal - authorized user
     * @return User - Entity
     */
    @PostMapping("/api/user/create-password")
    public ResponseEntity<?> createPassword(@CurrentUser UserPrincipal userPrincipal) {
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            User user = userService.findByLogin(userPrincipal.getLogin()).get();
            user.setLogin(userPrincipal.getLogin());
            user.setId(userPrincipal.getId());
            user.setPassword(passwordEncoder.encode(userPrincipal.getPassword()));
            User resultUser = userService.save(user);
            return new ResponseEntity<>(resultUser, HttpStatus.OK);
        } else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    private static String getWeekendsStr(List<Boolean> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i < list.size()-1) {
                result.append(list.get(i).toString()).append(",");
            } else {
                result.append(list.get(i).toString());
            }
        }
        return result.toString();
    }
}
