package com.example.demo.security;

import com.example.demo.dto.ResponseComDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String msg = authException.getMessage();
        String exceptionNameParts[] = authException.getClass().getName().split("\\.");
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
