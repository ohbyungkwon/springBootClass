package com.example.demo.security;

import com.example.demo.domain.User;
import com.example.demo.domain.enums.Gender;
import com.example.demo.enums.AuthProvider;
import com.example.demo.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails, OAuth2User {
    private User user;

    /* UserDetail*/
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getName() {
        return user.getName();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getBirth(){
        return user.getBirth();
    }

    public String getAddr(){
        return user.getAddr();
    }

    public Gender getGender(){
        return user.getGender();
    }

    public int getPoint(){
        return user.getPoint();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnable();
    }

    public AuthProvider getAuthProvider() {
        return user.getProvider();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        DateTime lastLoginTime = new DateTime(user.getLastLoginedDate());
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
        return Collections.singletonList(user.getRole());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }
}