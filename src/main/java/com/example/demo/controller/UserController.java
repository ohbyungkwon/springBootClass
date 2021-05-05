package com.example.demo.controller;

import com.example.demo.domain.User;
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
        if(user == null){
            return ResponseEntity.badRequest().build();
        }else{
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto.Create userDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }

        userService.createUser(userDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody UserDto.Update userDto, BindingResult bindingResult){
        User user = userService.searchUser(id);
        if(user == null){
            return ResponseEntity.badRequest().build();
        }else{
            if(bindingResult.hasErrors()){
                return ResponseEntity.badRequest().build();
            }

            if(userService.update(userDto, user) != null)
               return ResponseEntity.ok().build();
            else return ResponseEntity.badRequest().build();
        }
    }//비밀번호 수정

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        User user = userService.searchUser(id);
        if(user == null) {
            return ResponseEntity.badRequest().build();
        }
        else {
            userService.deleteUser(user);
            return ResponseEntity.ok().build();
        }
    }
}
