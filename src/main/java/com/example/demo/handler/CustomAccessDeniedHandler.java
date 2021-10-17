package com.example.demo.handler;

import com.example.demo.dto.HandlerDto;
import com.example.demo.service.common.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler{

    @Autowired
    private CommonService commonService;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                AccessDeniedException accessDeniedException) throws IOException, ServletException{

        HandlerDto param = HandlerDto.builder()
                .resultMsg(accessDeniedException.getMessage())
                .resultObject(null)
                .statusCode(HttpStatus.FORBIDDEN)
                .contentType("application/json;charset=UTF-8")
                .build();


        commonService.responseBuilder(response, param);
    }
}