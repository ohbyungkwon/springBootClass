package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.BadClientException;
import com.example.demo.exception.DuplicateException;
import com.example.demo.exception.NonRecommandUser;
import com.example.demo.repository.UserRepository;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        User user =  userRepository.findById(id);
        if(user == null){
            throw new BadClientException("사용자 정보가 없습니다.");
        }
        return user;
    }

    public void createUser(UserDto.Create userDto){
        if(userRepository.findById(userDto.getId()) != null){
            throw new DuplicateException("아이디 중복");
        }//중복 아이디 검증

        int point = 0;
        if(userDto.getRecommandUser() != null) {
            User recommandUser = userRepository.findByRecommandUser(userDto.getRecommandUser());
            if(recommandUser == null) throw new NonRecommandUser("추천 아이디 없음");

            point += 1000;
        }

        User newUser = User.builder()
                .id(userDto.getId())
                .pwd(passwordEncoder.encode(userDto.getPwd()))
                .name(userDto.getName())
                .email(userDto.getEmail())
                .birth(userDto.getBirth())
                .addr(userDto.getAddr())
                .gender(userDto.getGender())
                .point(point)
                .build();

        if(userRepository.save(newUser) == null){
            throw new InternalException("계정생성에 실패하였습니다.");
        }
    }

    public void updateUserPassword(UserDto.UpdatePassword userDto, String userId){
        User user = this.searchUser(userId);
        user.setPwd(userDto.getPwd());

        if(userRepository.save(user) != null){
            throw new InternalException("비밀번호 변경에 실패하였습니다.");
        }
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

        if(userRepository.save(user) != null){
            throw new InternalException("본사로 문의 부탁드립니다.");
        }

        return msg;
    }

    public void deleteUser(String userId){
        User user = this.searchUser(userId);
        userRepository.delete(user);
    }
}