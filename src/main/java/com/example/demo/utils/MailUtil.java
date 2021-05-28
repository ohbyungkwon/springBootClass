package com.example.demo.utils;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MailUtil {
    private JavaMailSenderImpl javaMailSender;
    private MimeMessage mimeMessage;
    private MimeMessageHelper mimeMessageHelper;

    public MailUtil(JavaMailSenderImpl sender) throws MessagingException {
        javaMailSender = sender;
        mimeMessage = sender.createMimeMessage();
        mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    }

    public void setFromEmail(String email) throws MessagingException {
        mimeMessageHelper.setFrom(email);
    }

    public void setToEmail(String ...email) throws MessagingException {
        mimeMessageHelper.setTo(email);
    }

    public void setSubject(String subject) throws MessagingException {
        mimeMessageHelper.setSubject(subject);
    }

    public void setText(String text) throws MessagingException {
        mimeMessageHelper.setText(text);
    }

    public void send() throws MessagingException {
        javaMailSender.send(mimeMessage);
    }
}
