package com.example.demo.security;

import com.example.demo.exception.CustAuthenticationException;
import com.example.demo.service.common.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class CustomJwtFilter extends GenericFilterBean {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Value("${token.header}")
    private String tokenHeader;

    @Value("${token.signkey}")
    private String signKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String uri = req.getRequestURI();
        if(!uri.equals("/login")) {
            String token = req.getHeader(tokenHeader);
            if (token == null) {
                throw new CustAuthenticationException("잘못된 접근입니다.");
            } else {
                if (!jwtTokenService.isUsable(signKey, token)) {
                    throw new CustAuthenticationException("토큰 만료.");
                }
            }
        }

        chain.doFilter(request, response);
    }
}
