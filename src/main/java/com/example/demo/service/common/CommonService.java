package com.example.demo.service.common;

import com.example.demo.dto.HandlerDto;
import com.example.demo.dto.ResponseComDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class CommonService {
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${token.header}")
    private String tokenHeader;

    public void responseBuilder(HttpServletResponse response, HandlerDto param) throws IOException {

        String json = objectMapper.writeValueAsString(
                ResponseComDto.builder()
                        .resultMsg(param.getResultMsg())
                        .resultObj(param.getResultObject())
                        .build());

        PrintWriter out = response.getWriter();
        response.setStatus(param.getStatusCode().value());
        response.setContentType(param.getContentType());
        response.setCharacterEncoding("UTF-8");

        if(param.getStatusCode() == HttpStatus.OK)
            response.setHeader(tokenHeader, param.getToken());

        out.print(json);
        out.flush();
    }

    public Boolean isOverTargetDate(Date targetDate){
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime targetDateTime = LocalDateTime.ofInstant(targetDate.toInstant(), ZoneId.systemDefault());

        return targetDateTime.isBefore(nowDateTime);
    }
}
