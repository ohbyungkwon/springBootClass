package com.example.demo.auth;

import com.example.demo.domain.User;
import com.example.demo.enums.AuthProvider;
import com.example.demo.exception.OAuth2AuthenticationProcessingException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class OAuth2CustomUserDetailService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            return processOAuthUser(userRequest, oAuth2User);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    /**
     * 사용자 정보 추출
     */
    private OAuth2User processOAuthUser(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String providerType = userRequest.getClientRegistration().getRegistrationId();
        if(!AuthProvider.kakao.toString().equals(providerType) &&
            !AuthProvider.naver.toString().equals(providerType)){
            throw new OAuth2AuthenticationProcessingException("카카오, 네이버 로그인만 지원합니다.");
        }

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes());
        String attemptedEmail = oAuth2UserInfo.getEmail();
        if (StringUtils.isEmpty(attemptedEmail)) {
            throw new OAuth2AuthenticationProcessingException("OAuth2 공급자(카카오, 네이버 등)에서 이메일을 찾을 수 없습니다.");
        }

        User user = Optional.ofNullable(userRepository.findByEmail(oAuth2UserInfo.getEmail()))
                .orElseThrow(() -> new OAuth2AuthenticationProcessingException("로그인 정보가 틀립니다."));

        return new CustomUserDetails(user);
    }
}
