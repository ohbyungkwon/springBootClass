package com.example.demo.service.common;

import com.example.demo.security.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;

import java.util.Date;

@Slf4j
@Service
public class JwtTokenService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${redis.gubun.name}")
    private String gubun;

    public String generateToken(String signKey, Authentication authentication) throws Exception {
        CustomUserDetails userDetails = ((CustomUserDetails) authentication.getPrincipal());
        String subject = objectMapper.writeValueAsString(userDetails);
        DateTime currentDate = new DateTime();

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(currentDate.toDate())
                .setExpiration(currentDate.plusMinutes(60).toDate())
                .signWith(SignatureAlgorithm.HS256, signKey)
                .compact();

        String username = userDetails.getUsername();
        redisService.setString(username + gubun + "TOKEN", token);
        return token;
    }

    public boolean isUsable(String signKey, String token){
        Jws<Claims> claims = null;
        JSONObject userInfo = null;
        Date lastAcccessTime = null;

        String username = null;
        boolean isUsable = true;
        try {
            claims = this.getClaims(signKey, token);
            userInfo = this.getLoginUserInfo(claims.getBody());
            username = (String) userInfo.get("username");
            lastAcccessTime = new Date();
        } catch(ExpiredJwtException e) {
            try {
                //token 만료되어도 계속 동작이 있었는지 체크(Session Sliding)
                userInfo = this.getLoginUserInfo(e.getClaims());
                username = (String) userInfo.get("username");

                DateTime currentDate = new DateTime();
                lastAcccessTime = DateTime.parse(redisService.getString(username + gubun + "TIME")).toDate();

                //30분 동안 동작이 없었을 때 or 토큰만료 시간이 하루 이상 지날 떄
                if (currentDate.minusMinutes(30).toDate().compareTo(lastAcccessTime) > 0 ||
                        new DateTime(e.getClaims().getExpiration()).plusDays(1).isBeforeNow()) {
                    this.removeCacheInfo(username);
                    isUsable = false;
                } else {
                    lastAcccessTime = new Date();
                }
            } catch (Exception e1){
                isUsable = false;
                e1.printStackTrace();
            }
        } catch (Exception e2){
            isUsable = false;
            e2.printStackTrace();
        }

        if(isUsable)
            setLastAccessTime(username, lastAcccessTime.toString());

        return isUsable;
    }

    private Jws<Claims> getClaims(String signKey, String token) throws Exception {
        return Jwts.parser().setSigningKey(signKey).parseClaimsJws(token);
    }

    private JSONObject getLoginUserInfo(Claims claims) throws Exception{
        return new JSONObject(claims.getSubject());
    }

    private void setLastAccessTime(String username, String newDate) {
        try {
            redisService.setString(username + gubun + "TIME", newDate);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void removeCacheInfo(String username) throws Exception {
        redisService.removeKey(username + gubun + "TOKEN");  // Current Token 삭제
        redisService.removeKey(username + gubun + "TIME"); // Access Time 삭제
    }
}
