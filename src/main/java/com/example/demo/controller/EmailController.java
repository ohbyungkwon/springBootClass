package com.example.demo.controller;

import com.example.demo.dto.MailDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class EmailController extends AbstractController{
    private UserService userService;

    @Autowired
    EmailController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/sendAuthEmail")
    public ResponseEntity<?> sendAuthEmail(@Valid @RequestBody MailDto.SendEmail sendEmail, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }


        return null;
    }

    @PostMapping("/sendAuthEmail")
    public ResponseEntity<?> checkAuthEmail(@Valid @RequestBody MailDto.AuthEmail authEmail, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }


        return null;
    }
}
