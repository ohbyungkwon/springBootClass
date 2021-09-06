package com.example.demo.service;

import com.example.demo.annotation.CustTransaction;
import com.example.demo.domain.User;
import com.example.demo.dto.MailDto;
import com.example.demo.enums.Role;
import com.example.demo.exception.BadClientException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.common.RedisService;
import com.example.demo.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;

@Service
public class MailSenderService {

    @Value("$(mail.smtp.username)")
    private String fromEmail;

    @Value("${redis.gubun.name}")
    private String gubun;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRepository userRepository;

    @CustTransaction
    public void sendMailToAuth(Object mailDto, Principal principal) throws Exception{
        String username = principal.getName();
        User user = userRepository.findById(username);

        String authCode = UUID.randomUUID().toString().substring(0, 10);

        MailUtil mailUtil = new MailUtil(new JavaMailSenderImpl());
        mailUtil.setFromEmail(fromEmail);
        if(mailDto instanceof MailDto.SendEmail){
            MailDto.SendEmail dto = (MailDto.SendEmail) mailDto;
            if(!user.getEmail().equals(dto.getEmail())){
                throw new BadClientException("회원가입시 기입한 이메일과 다릅니다.");
            }

            mailUtil.setSubject("인증 메일입니다.");
            mailUtil.setText("인증번호 : " +  authCode);
            mailUtil.setToEmail(dto.getEmail());

            redisService.setString(username + gubun + "AUTH", authCode);
        }

        mailUtil.send();
    }

    @CustTransaction
    public void checkAuthCode(MailDto.AuthEmail authEmail, Principal principal) throws Exception{
        String username = principal.getName();
        User user = userRepository.findById(username);
        if(user.getRole() != Role.UNAUTHORIZATION_ROLE){
            throw new BadClientException("인증 대상이 아닙니다.");
        }

        String authCode = redisService.getString(username + gubun + "AUTH");
        if(!authCode.equals(authEmail.getCode())){
            throw new BadClientException("인증번호를 확인해주세요.");
        }

        user.setRole(Role.NORMAL_ROLE);
        userRepository.save(user);

        redisService.removeKey(username + gubun + "AUTH");
    }
}
