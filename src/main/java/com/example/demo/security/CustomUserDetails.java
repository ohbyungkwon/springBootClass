package com.example.demo.security;

import com.example.demo.domain.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(user.getRole());
    }

    @Override
    public String getPassword() {
        return user.getPwd();
    }

    @Override
    public String getUsername() {
        return user.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        DateTime lastLoginTime = new DateTime(user.getLastLogined());
        return (new Date().getTime() < lastLoginTime.plusDays(30).toDate().getTime());
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        DateTime modifiedDate = new DateTime(user.getModifyDate());
        return new Date().getTime() < modifiedDate.plusDays(90).toDate().getTime();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnable();
    }
}
