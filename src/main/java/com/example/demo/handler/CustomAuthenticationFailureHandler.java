package com.example.demo.handler;

import com.example.demo.domain.LoginHistory;
import com.example.demo.dto.ResponseComDto;
import com.example.demo.repository.LoginHistoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final String jsonType = ContentType.APPLICATION_JSON.getMimeType();

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        String reqType = request.getHeader("Content-Type");
        if(reqType.equals(jsonType)) {
            username = String.valueOf(request.getAttribute("username"));
        }

        int errorCnt = loginHistoryRepository.countLoginHistoryByUsername(username, false);
        if(errorCnt <= 5) {
            LoginHistory loginHistory = LoginHistory.builder()
                    .username(username)
                    .isSuccess(false)
                    .build();

            loginHistoryRepository.save(loginHistory);
        }

        String msg = exception.getMessage();
        String exceptionNameParts[] = exception.getClass().getName().split("\\.");
        String exceptionName = exceptionNameParts[exceptionNameParts.length - 1];
        int code = HttpStatus.BAD_REQUEST.value();

        String json = objectMapper.writeValueAsString(
                ResponseComDto.builder()
                .resultMsg(msg)
                .resultObj(exceptionName)
                .build());

        PrintWriter out = response.getWriter();
        response.setStatus(code);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }
}
