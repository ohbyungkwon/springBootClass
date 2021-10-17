package com.example.demo.auth;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class KakaoOAuth2UserInfo extends OAuth2UserInfo {
    KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getName() {
        Map<String, String> map = (HashMap<String, String>) attributes.get("properties");
        return map.get("nickname");
    }

    @Override
    public String getEmail() {
        Map<String, Object> map = (HashMap<String, Object>) attributes.get("kakao_account");
        boolean hasEmail = (boolean) map.get("has_email");
        if(hasEmail)
            return (String) map.get("email");
        else return null;
    }

    @Override
    public String getImageUrl() {
        Map<String, String> map = (HashMap<String, String>) attributes.get("properties");
        return map.get("profile_image");
    }
}