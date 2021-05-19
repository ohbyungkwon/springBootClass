package com.example.demo.aop;

import com.example.demo.annotation.CustTransaction;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.transaction.TransactionManager;

@Aspect
@Slf4j
@Component
public class TransactionAspect {
    @Autowired
    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager;

    /*
    추가 TransactionManager가 있을 때
    @Autowired
    @Qualifier("defaultTransactionManager")
    private PlatformTransactionManager transactionManager;
    */

    @Around("@annotation(com.example.demo.annotation.CustTransaction) && @annotation(input)")
    public Object setLog(ProceedingJoinPoint joinPoint, CustTransaction input) throws Throwable {
        String selectedTransaction = input.toString();

        TransactionStatus status = null;
        DefaultTransactionDefinition defaultTran = new DefaultTransactionDefinition();
        defaultTran.setReadOnly(input.isReadOnly());

        PlatformTransactionManager tmp = transactionManager;

        Object result = null;
        status = tmp.getTransaction(defaultTran);
        log.info("Transaction START : {}", status.isNewTransaction());
        try{
            result = joinPoint.proceed();
        } catch (Exception e){
            e.printStackTrace();
            tmp.rollback(status);
        }

        tmp.commit(status);
        log.info("Transaction END");

        return result;
    }
}