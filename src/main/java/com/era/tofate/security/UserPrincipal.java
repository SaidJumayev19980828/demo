package com.era.tofate.security;

import com.era.tofate.entities.user.User;
import com.era.tofate.entities.userrole.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UserPrincipal implements UserDetails {
    private Long id;
    private String login;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(Long id, String login, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }

    public UserPrincipal(Long id, String login, String oldPassword) {
        this.id = id;
        this.login = login;
        this.password = oldPassword;
    }

    static UserPrincipal create(User user, List<UserRole> userRoles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (UserRole userRole : userRoles) {
            authorities.add(new SimpleGrantedAuthority(userRole.getRole().name()));
        }

        return new UserPrincipal(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                authorities);
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user, new ArrayList<>());
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    private void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                ", attributes=" + attributes +
                '}';
    }
}
