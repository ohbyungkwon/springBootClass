package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.ResponseComDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class UserController extends AbstractController{
    private UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users/me")
    public ResponseEntity<?> searchUser(Principal principal){
        User user = userService.searchUser(principal.getName());

        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                .resultMsg("")
                .resultObj(user)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto.Create userDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }

        userService.createUser(userDto);

        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                .resultMsg("계정 생성이 완료되었습니다.")
                .resultObj(null)
                .build(), HttpStatus.OK);
    }//회원가입

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(Principal principal, @Valid @RequestBody UserDto.Update userDto,
                                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }

        String msg = userService.updateUser(userDto, principal.getName());

        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg(msg)
                        .resultObj(null)
                        .build(), HttpStatus.OK);
    }//계정 수정

    @PutMapping("/user/pw")
    public ResponseEntity<?> updateUserPassword(Principal principal, @Valid @RequestBody UserDto.UpdatePassword userDto,
                                        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }

        userService.updateUserPassword(userDto, principal.getName());

        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("비밀번호 변경이 완료되었습니다.")
                        .resultObj(null)
                        .build(), HttpStatus.OK);
    }//비밀번호 수정

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(Principal principal){
        userService.deleteUser(principal.getName());
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("회원탈퇴가 완료되었습니다.")
                        .resultObj(null)
                        .build(), HttpStatus.OK);
    }//계정 삭제
}
