package com.example.demo.handler;

import com.example.demo.domain.LoginHistory;
import com.example.demo.dto.HandlerDto;
import com.example.demo.enums.Role;
import com.example.demo.repository.LoginHistoryRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.common.CommonService;
import com.example.demo.service.common.JwtTokenService;
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

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private CommonService commonService;

    @Value("${token.signkey}")
    private String signKey;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

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
        HttpStatus statusCode = HttpStatus.OK;
        if(authentication.getAuthorities().contains(Role.UNAUTHORIZATION_ROLE)){
            resultMsg = "이메일 인증이 필요합니다.";
            statusCode = HttpStatus.UNAUTHORIZED;
            //해당 statusCode를 받고 clients는 이메일 인증 화면으로 전환 필요.
        }

        HandlerDto param = HandlerDto.builder()
                .resultMsg(resultMsg)
                .resultObject(userDetails)
                .contentType("application/json")
                .statusCode(statusCode)
                .token(token)
                .build();

        commonService.responseBuilder(response, param);
    }
}
