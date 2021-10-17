package com.example.demo.security;

import com.example.demo.domain.User;
import com.example.demo.enums.AuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.security.auth.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Provider;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails, OAuth2User {
    private User user;

    /* UserDetail*/
    @Override
    @JsonIgnore
    public String getPassword() {
        return user.getPw();
    }

    @Override
    public String getUsername() {
        return user.getId();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnable();
    }

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        DateTime lastLoginTime = new DateTime(user.getLastLogined());
        return (new Date().getTime() < lastLoginTime.plusDays(30).toDate().getTime());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        DateTime modifiedDate = new DateTime(user.getModifyDate());
        return new Date().getTime() < modifiedDate.plusDays(90).toDate().getTime();
    }

    /* OAuth2User */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(user.getRole());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }


    /* Custom */
    public String getEmail() {
        return user.getEmail();
    }

    public AuthProvider getProvider() {
        return user.getProvider();
    }
}