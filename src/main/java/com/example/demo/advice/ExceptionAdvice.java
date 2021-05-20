//package com.example.demo.advice;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Slf4j
//@ControllerAdvice
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class ExceptionAdvice {
//
//    @ResponseBody
//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<?> handlerBadRequestException(Exception e) {
//        return new ResponseEntity<>(new Object(), HttpStatus.BAD_REQUEST);
//    }
//}