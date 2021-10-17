package com.example.demo.handler;

import com.example.demo.domain.LoginHistory;
import com.example.demo.dto.HandlerDto;
import com.example.demo.repository.LoginHistoryRepository;
import com.example.demo.service.common.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private CommonService commonService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String username = String.valueOf(request.getAttribute("username"));
        int errorCnt = loginHistoryRepository.countLoginHistoryByUsername(username, false);
        if (errorCnt <= 5) {
            LoginHistory loginHistory = LoginHistory.builder()
                    .username(username)
                    .isSuccess(false)
                    .build();

            loginHistoryRepository.save(loginHistory);
        }

        String resultMsg = exception.getMessage();
        String exceptionNameParts[] = exception.getClass().getName().split("\\.");
        String exceptionName = exceptionNameParts[exceptionNameParts.length - 1];

        HandlerDto param = HandlerDto.builder()
                .resultMsg(resultMsg)
                .resultObject(exceptionName)
                .contentType("application/json")
                .statusCode(HttpStatus.BAD_REQUEST)
                .build();

        commonService.responseBuilder(response, param);
    }
}
