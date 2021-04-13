package com.era.tofate.security;

import com.era.tofate.entities.user.User;
import com.era.tofate.entities.userrole.UserRole;
import com.era.tofate.service.user.UserService;
import com.era.tofate.service.userrole.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        User user = userService.findByLogin(login)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with login : " + login)
                );
        List<UserRole> userRoles = userRoleService.findAllByUser_Login(user.getLogin());
        return UserPrincipal.create(user, userRoles);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userService.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User not found with id : " + id)
        );
        List<UserRole> userRoles = userRoleService.findAllByUser_Login(user.getLogin());
        return UserPrincipal.create(user, userRoles);
    }
}
