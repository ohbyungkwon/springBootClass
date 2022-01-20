package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.BadClientException;
import com.example.demo.exception.DuplicateException;
import com.example.demo.exception.NonRecommandUser;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User searchUser(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BadClientException("사용자 정보가 없습니다."));
    }

    @Transactional
    public User createUser(UserDto.Create userDto){
        Boolean isExistUser = userRepository.existsByUsername(userDto.getUsername());
        if(isExistUser) {
            throw new DuplicateException("아이디 중복");
        }

        String password = passwordEncoder.encode(userDto.getPassword());
        User newUser = User.create(userDto, password);

        if(userDto.getRecommandUser() != null) {
            User recommendedUser = userRepository.findByUsername(userDto.getRecommandUser())
                    .orElseThrow(() -> new NonRecommandUser("추천 아이디 없음"));
            newUser.recommend(recommendedUser);
        }

        return userRepository.save(newUser);
    }

    @Transactional
    public User updateUserPassword(UserDto.UpdatePassword userDto, String userId){
        User user = this.searchUser(userId);

        String password = passwordEncoder.encode(userDto.getPassword());
        user.updatePassword(password);

        return user;
    }

    @Transactional
    public String updateUser(UserDto.Update userDto, String username){
        User user = this.searchUser(username);

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
        return msg;
    }

    @Transactional
    public void deleteUser(String userId){
        User user = this.searchUser(userId);
        userRepository.delete(user);
    }
}