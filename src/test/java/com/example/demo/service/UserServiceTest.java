package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.domain.enums.Gender;
import com.example.demo.dto.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    @Rollback(value = false)
    public void searchUser() {
        this.createUser();
        userService.searchUser("obkTest");
    }

    @Test
    @Rollback(value = false)
    public void createUser(){
        User savedUser = this.userBuilder();
        Assertions.assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    @Rollback(value = false)
    public void updatePassword() {
        User savedUser = this.userBuilder();

        String newPassword = "1q2w3e4r";
        UserDto.UpdatePassword dto = new UserDto.UpdatePassword();
        dto.setPassword(newPassword);
        User updatedUser = userService.updateUserPassword(dto, "obkTest");

        Assertions.assertThat(savedUser.getPassword()).isNotEqualTo(updatedUser.getPassword());
    }

    public User userBuilder(){
        UserDto.Create createDto = new UserDto.Create();
        createDto.setUsername("obkTest");
        createDto.setPassword("1234");
        createDto.setName("obk");
        createDto.setEmail("test@test.com");
        createDto.setAddr("1");
        createDto.setBirth("1");
        createDto.setGender(Gender.MAN);

        return userService.createUser(createDto);
    }
}