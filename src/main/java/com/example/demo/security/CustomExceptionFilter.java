package com.example.demo.security;

import com.example.demo.dto.ResponseComDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomExceptionFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (Exception ex){
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex){
        log.info("errCode: {}, message: {}", status, ex.getMessage());
        response.setStatus(status.value());
        response.setContentType("application/json");

        try{
            String json = objectMapper.writeValueAsString(
                    ResponseComDto.builder()
                            .resultMsg(ex.getMessage())
                            .resultObj(null)
                            .build());

            response.getWriter().write(json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
