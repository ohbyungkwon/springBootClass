package com.example.demo.controller;

import com.example.demo.dto.MailDto;
import com.example.demo.dto.ResponseComDto;
import com.example.demo.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class MailController extends AbstractController{
    private MailSenderService mailSenderService;

    @Autowired
    MailController(MailSenderService mailSenderService){
        this.mailSenderService = mailSenderService;
    }

    @PostMapping("/sendAuthEmail")
    public ResponseEntity<?> sendAuthEmail(@Valid @RequestBody MailDto.SendEmail sendEmail, BindingResult bindingResult, Principal principal) throws Exception {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        this.mailSenderService.sendMailToAuth(sendEmail, principal);

        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("메일이 전송되었습니다.")
                        .resultObj(null)
                        .build(), HttpStatus.OK);
    }

    @PostMapping("/checkAuthEmail")
    public ResponseEntity<?> checkAuthEmail(@Valid @RequestBody MailDto.AuthEmail authEmail, BindingResult bindingResult, Principal principal) throws Exception {
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }

        this.mailSenderService.checkAuthCode(authEmail, principal);

        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("인증이 완료되었습니다. 다시 로그인해주세ㅐ요.")
                        .resultObj(null)
                        .build(), HttpStatus.OK);
    }
}
