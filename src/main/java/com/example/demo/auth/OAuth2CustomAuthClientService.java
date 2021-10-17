package com.example.demo.auth;

import com.example.demo.domain.User;
import com.example.demo.enums.AuthProvider;
import com.example.demo.enums.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class OAuth2CustomAuthClientService implements OAuth2AuthorizedClientService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        return null;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        OAuth2RefreshToken refreshToken = authorizedClient.getRefreshToken();

        CustomUserDetails userDetails = (CustomUserDetails) principal.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getEmail());

        user.setAccessToken(accessToken.getTokenValue());
        user.setRefreshToken(refreshToken.getTokenValue());
        user.setProvider(userDetails.getProvider());
        user.setRole(Role.NORMAL_ROLE);//OAuth2 계정은 이미 이메일은 인증한 것으로 가정(UNAUTHORIZATION_ROLE 생략)
        User saveUser = userRepository.save(user);
        log.info("user: {}", saveUser);

        request.setAttribute("username", userDetails.getUsername());
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {

    }
}
