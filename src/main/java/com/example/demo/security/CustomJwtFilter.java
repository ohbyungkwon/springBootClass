package com.example.demo.security;

import com.example.demo.exception.CustAuthenticationException;
import com.example.demo.service.common.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomJwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Value("${token.header}")
    private String tokenHeader;

    @Value("${token.signkey}")
    private String signKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(!uri.equals("/login")) {
            String token = request.getHeader(tokenHeader);
            if (token != null) {
                if (!jwtTokenService.isUsable(signKey, token)) {
                    throw new CustAuthenticationException("토큰 만료.");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
