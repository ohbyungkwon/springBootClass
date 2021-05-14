package com.example.demo.handler;

import com.example.demo.domain.LoginHistory;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustAuthenticationException;
import com.example.demo.repository.LoginHistoryRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String username = request.getParameter("username");
        if(exception instanceof BadCredentialsException){
            int errorCnt = loginHistoryRepository.countLoginHistoryByUsername(username);
            if(errorCnt <= 5){
                LoginHistory loginHistory = LoginHistory.builder()
                        .username(username)
                        .isSuccess(false)
                        .build();

                loginHistoryRepository.save(loginHistory);

                UserDto.Update updateDto = new UserDto.Update();
                updateDto.setIsEnable(false);
                String msg = userService.updateUser(updateDto, username);
                throw new CustAuthenticationException(msg);
            }
        }
    }
}
