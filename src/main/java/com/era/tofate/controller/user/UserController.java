package com.era.tofate.controller.user;

import com.era.tofate.entities.socialstatus.SocialStatus;
import com.era.tofate.entities.user.User;
import com.era.tofate.entities.userrole.UserRole;
import com.era.tofate.enums.Role;
import com.era.tofate.enums.RoleDto;
import com.era.tofate.enums.Sex;
import com.era.tofate.exceptions.BadRequestException;
import com.era.tofate.payload.user.UserRequest;
import com.era.tofate.payload.user.UserResponse;
import com.era.tofate.payload.user.UserResponseDto;
import com.era.tofate.payload.user.UserRoleDto;
import com.era.tofate.security.CurrentUser;
import com.era.tofate.security.UserPrincipal;
import com.era.tofate.service.socialstatus.SocialStatusService;
import com.era.tofate.service.user.UserService;

import java.util.Arrays;
import java.util.List;

import com.era.tofate.service.userrole.UserRoleService;
import com.google.common.collect.Sets;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

import static com.era.tofate.exceptions.ExceptionConstants.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;
    private final SocialStatusService socialStatusService;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;


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
            Optional<String>        password = Optional.ofNullable(userRequest.getPassword());


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
            password.ifPresent(pass -> currentUser.setPassword(passwordEncoder.encode(pass)));
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
    public ResponseEntity<?> getAccount(@CurrentUser UserPrincipal userPrincipal) {
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

    /**
     * Create user - Operator - Manager (only for admin)
     *
     * @param userPrincipal - authorized user
     * @return User - Entity
     */
    @PostMapping("/api/admin/user")
    public ResponseEntity<?> createManagerOrOperator(@CurrentUser UserPrincipal userPrincipal, @RequestBody UserRoleDto userDto){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            if (roleExists(userDto.getRoles(), Role.MANAGER) || roleExists(userDto.getRoles(), Role.OPERATOR)){
                return createUser(userDto);
            }
            throw new BadRequestException(INVALID_ROLE);
        } else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    @GetMapping("/api/admin/user/byRole")
    @ApiOperation(value = "Get users by role with paging",
            notes = "Returns list of users by given paging. Only for OPERATOR and MANAGER")
    public ResponseEntity<?> getUsersByRole(@CurrentUser UserPrincipal userPrincipal,
                                            @RequestParam RoleDto role,
                                            @RequestParam int page,
                                            @RequestParam int pageSize){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            return ResponseEntity.ok(userService.findAllByRolesContaining(role,page,pageSize));
        } else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    private ResponseEntity<?> createUser(UserRoleDto userDto){
        if (userService.findByLogin(userDto.getLogin()).isPresent()){
            throw new BadRequestException(LOGIN_ALREADY_IN_USE);
        }
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user = userService.save(user);
        for (UserRole role : userDto.getRoles()) {
            UserRole userRole = new UserRole(user,role.getRole());
            userRoleService.save(userRole);
        }
        return new ResponseEntity<>(new UserResponseDto(user), HttpStatus.OK);
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

    private boolean roleExists(Set<UserRole> roles,Role role){
        for (UserRole userRole : roles) {
            if (userRole.getRole().equals(role)){
                return true;
            }
        }
        return false;
    }

}
