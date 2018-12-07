package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.DuplicateException;
import com.example.demo.exception.NonRecommandUser;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.util.Password;

import java.util.List;

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
        return userRepository.findById(id);
    }

    public void createUser(UserDto.Create userDto){
        if(userRepository.findById(userDto.getId()) != null){
            throw new DuplicateException("아이디 중복");
        }//중복 아이디 검증

        int point = 0;
        if(userDto.getRecommandUser() != null) {
            User user = userRepository.findByRecommandUser(userDto.getRecommandUser());
            if(user == null) throw new NonRecommandUser("해당 아이디 없음");
            //excpetion은 위로 뺸다.

            point += 1000;
        }
        User user = User.builder()
                .id(userDto.getId())
                .pwd(passwordEncoder.encode(userDto.getPwd()))
                .name(userDto.getName())
                .email(userDto.getEmail())
                .birth(userDto.getBirth())
                .addr(userDto.getAddr())
                .gender(userDto.getGender())
                .point(point)
                .build();

        userRepository.save(user);
    }

    public User update(UserDto.Update userDto, User user){
        user.builder()
                .pwd(userDto.getPwd())
                .build();
        return userRepository.save(user);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }
}

//TODO validataion check, 가입시에 추천인 코드를 입력하면 포인트 더 주는 로직과 예외처리