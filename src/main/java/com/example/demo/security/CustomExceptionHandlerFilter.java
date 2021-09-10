package com.example.demo.security;

import com.example.demo.dto.ResponseComDto;
import com.example.demo.exception.CustAuthenticationException;
import com.example.demo.service.common.JwtTokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomExceptionHandlerFilter extends OncePerRequestFilter {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (RuntimeException e){
            setResponse(response, e);
        } catch (Exception e){
            setResponse(response, e);
        }
    }

    public void setResponse(HttpServletResponse response, Exception exception) throws JsonProcessingException {
        String msg = exception.getMessage();
        String exceptionNameParts[] = exception.getClass().getName().split("\\.");
        String exceptionName = exceptionNameParts[exceptionNameParts.length - 1];
        int code = HttpStatus.BAD_REQUEST.value();

        String json = objectMapper.writeValueAsString(
                ResponseComDto.builder()
                        .resultMsg(msg)
                        .resultObj(exceptionName)
                        .build());

        response.setStatus(code);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
