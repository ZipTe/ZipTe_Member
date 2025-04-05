package com.zipte.member.security.oauth2.domain.google;

import com.zipte.member.security.oauth2.domain.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class GoogleUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes; // getAttributes()

    @Override
    public String getProvider() {
        return "Google";
    }

    @Override
    public String getProviderId() {
        return "";
    }

    @Override
    public String getEmail() {
        return "";
    }

    @Override
    public String getUserName() {
        return "";
    }

    @Override
    public String getImageUrl() {
        return "";
    }
}
