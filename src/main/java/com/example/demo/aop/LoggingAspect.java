package com.example.demo.aop;

import com.example.demo.controller.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Around("execution(* com.example.demo..*Controller.*(..))")
    public Object setLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long start = System.currentTimeMillis();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //application request

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Object[] objects = proceedingJoinPoint.getArgs();
        StringBuilder params = new StringBuilder();
        for(Object signatureArg : objects){
            if(!(signatureArg instanceof Principal)){
                params.append("/").append(signatureArg);
            }
        }

        log.info("'{}' params : {},  user_id : {}", proceedingJoinPoint.getSignature().getName(), params, principal);


        Object result = proceedingJoinPoint.proceed();//샐행

        long end = System.currentTimeMillis();

        return result;
    }
}
