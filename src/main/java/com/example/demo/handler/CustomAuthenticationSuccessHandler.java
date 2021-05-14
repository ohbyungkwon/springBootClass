package com.example.demo.handler;

import com.example.demo.domain.LoginHistory;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustAuthenticationException;
import com.example.demo.repository.LoginHistoryRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = request.getParameter("username");

        LoginHistory loginHistory = LoginHistory.builder()
                .username(username)
                .isSuccess(true)
                .build();

        loginHistoryRepository.save(loginHistory);
    }
}
