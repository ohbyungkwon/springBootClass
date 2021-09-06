package com.example.demo.advice;

import com.example.demo.dto.ResponseComDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handlerBadRequestException(Exception e) {
        List<String> exceptionParts = Arrays.asList(e.getClass().getName().toString().split("\\."));
        String exceptionName = exceptionParts.get(exceptionParts.size() - 1);

        log.info("#############################");
        log.info("Exception: {}", exceptionName);
        log.info("#############################");

        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "본사로 문의 바랍니다.";
        if(exceptionName.equals("BadClientException")){
            code = HttpStatus.BAD_REQUEST;
            message = e.getMessage();
        }
        //Exception 처리 로직 이어 작성

        ResponseComDto body = new ResponseComDto(null, message);

        return new ResponseEntity<>(body, code);
    }
}