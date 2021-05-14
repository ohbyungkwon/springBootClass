package com.example.demo.exception;

import org.springframework.security.core.AuthenticationException;

public class CustAuthenticationException extends AuthenticationException {
    public CustAuthenticationException(String msg) {
        super(msg);
    }
}
