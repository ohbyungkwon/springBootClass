package com.example.demo.security;

import com.example.demo.domain.User;
import com.example.demo.exception.CustAuthenticationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomUsernamePasswordFilter extends UsernamePasswordAuthenticationFilter {
    private static final String jsonType = ContentType.APPLICATION_JSON.getMimeType();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(!request.getMethod().equals("POST")){
            throw new CustAuthenticationException("POST 요청만 지원합니다.");
        }

        String username = null, password =  null;
        String reqType = request.getHeader("Content-Type");
        if(reqType.equals(jsonType)){
             ObjectMapper mapper = new ObjectMapper();
            try {
                Map<String, String> map = mapper.readValue(request.getReader().lines().collect(Collectors.joining()),
                        new TypeReference<Map<String, String>>(){});

                String usernameKey = super.getUsernameParameter();;
                String passwordKey = super.getPasswordParameter();

                username = Optional.ofNullable(map.get(usernameKey)).orElse("");
                password = Optional.ofNullable(map.get(passwordKey)).orElse("");
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustAuthenticationException("파싱 오류.");
            }
        } else {
            //기존 처리 방식
            username = Optional.ofNullable(this.obtainUsername(request)).orElse("");
            password = Optional.ofNullable(this.obtainPassword(request)).orElse("");
        }

        request.setAttribute("username", username);

        User user = new User();
        user.setId(username);

        UsernamePasswordAuthenticationToken tmpAuthenticationToken = new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), password);
        this.setDetails(request, tmpAuthenticationToken);

        return this.getAuthenticationManager().authenticate(tmpAuthenticationToken);
    }
}
