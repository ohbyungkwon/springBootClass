package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.enums.Role;
import com.example.demo.exception.BadClientException;
import com.example.demo.exception.DuplicateException;
import com.example.demo.exception.NonRecommandUser;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User searchUser(String id){
        return Optional.ofNullable(userRepository.findById(id)).orElseThrow(() ->
                    new BadClientException("사용자 정보가 없습니다.")
                );
    }

    public void createUser(UserDto.Create userDto){
        if(userRepository.findById(userDto.getId()) != null) {
            throw new DuplicateException("아이디 중복");
        }

        int point = 0;
        if(userDto.getRecommandUser() != null) {
            Optional.ofNullable(userRepository.findById(userDto.getRecommandUser())).orElseThrow(() ->
                new NonRecommandUser("추천 아이디 없음")
            );
            point += 1000;
        }

        Role role = Optional.ofNullable(userDto.getRole()).orElse(Role.UNAUTHORIZATION_ROLE);
        User newUser = User.builder()
                .id(userDto.getId())
                .pwd(passwordEncoder.encode(userDto.getPwd()))
                .name(userDto.getName())
                .email(userDto.getEmail())
                .birth(userDto.getBirth())
                .addr(userDto.getAddr())
                .gender(userDto.getGender())
                .point(point)
                .role(role)
                .build();

        userRepository.save(newUser);
    }

    public void updateUserPassword(UserDto.UpdatePassword userDto, String userId){
        User user = this.searchUser(userId);
        user.setPwd(userDto.getPwd());

        userRepository.save(user);
    }

    public String updateUser(UserDto.Update userDto, String userId){
        User user = this.searchUser(userId);

        String msg = "";
        String addr = userDto.getAddr();
        if(!addr.isEmpty()){
            user.setAddr(addr);
            msg = "주소가 변경되었습니다.";
        }

        boolean isEnable = userDto.getIsEnable();
        if(user.getIsEnable() != isEnable){
            user.setIsEnable(isEnable);
            msg = "계정이 잠겼습니다.";
        }

        userRepository.save(user);

        return msg;
    }

    public void deleteUser(String userId){
        User user = this.searchUser(userId);
        userRepository.delete(user);
    }
}