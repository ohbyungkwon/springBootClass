package com.example.demo.handler;

import com.example.demo.domain.LoginHistory;
import com.example.demo.dto.ResponseComDto;
import com.example.demo.enums.Role;
import com.example.demo.repository.LoginHistoryRepository;
import com.example.demo.service.common.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final String jsonType = ContentType.APPLICATION_JSON.getMimeType();

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Value("${token.header}")
    private String tokenHeader;

    @Value("${token.signkey}")
    private String signKey;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = request.getParameter("username");
        String reqType = request.getHeader("Content-Type");
        if(reqType.equals(jsonType)) {
            username = authentication.getPrincipal().toString();
        }

        LoginHistory loginHistory = LoginHistory.builder()
                .username(username)
                .isSuccess(true)
                .build();
        loginHistoryRepository.save(loginHistory);

        String token = null;
        try {
            token = jwtTokenService.generateToken(signKey, authentication);
        } catch (Exception e){
            e.printStackTrace();
        }

        String resultMsg = "로그인하였습니다.";
        int statusCode = HttpStatus.OK.value();
        if(authentication.getAuthorities().contains(Role.UNAUTHORIZATION_ROLE)){
            resultMsg = "이메일 인증이 필요합니다.";
            statusCode = HttpStatus.UNAUTHORIZED.value();
            //해당 statusCode를 받고 clients는 이메일 인증 화면으로 전환 필요.
        }

        String json = objectMapper.writeValueAsString(
                ResponseComDto.builder()
                        .resultMsg(resultMsg)
                        .resultObj(authentication)
                        .build());

        PrintWriter out = response.getWriter();
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader(tokenHeader, token);

        out.print(json);
        out.flush();

    }
}
