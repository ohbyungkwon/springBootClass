package com.example.demo.handler;

import com.example.demo.domain.LoginHistory;
import com.example.demo.dto.ResponseComDto;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.CustAuthenticationException;
import com.example.demo.repository.LoginHistoryRepository;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final String jsonType = ContentType.APPLICATION_JSON.getMimeType();

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = request.getParameter("username");

        String reqType = request.getHeader("Content-Type");
        if(reqType.equals(jsonType)) {
            username = String.valueOf(request.getAttribute("username"));
        }

        LoginHistory loginHistory = LoginHistory.builder()
                .username(username)
                .isSuccess(true)
                .build();

        loginHistoryRepository.save(loginHistory);

        String json = objectMapper.writeValueAsString(
                ResponseComDto.builder()
                        .resultMsg("로그인하였습니다.")
                        .resultObj(authentication)
                        .build());

        PrintWriter out = response.getWriter();
        response.setStatus(HttpStatus.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        out.print(json);
        out.flush();

    }
}
