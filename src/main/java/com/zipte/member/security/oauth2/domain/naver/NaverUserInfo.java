package com.zipte.member.security.oauth2.domain.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zipte.member.security.oauth2.domain.OAuth2UserInfo;

import java.util.Map;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attribute;

    @SuppressWarnings("unchecked")
    public NaverUserInfo(Map<String, Object> attributes) {

        // `response`가 Map<String, Object> 형식인지 확인 후 안전하게 캐스팅
        Object response = attributes.get("response");
        if (response instanceof Map) {
            this.attribute = (Map<String, Object>) response;
        } else {
            throw new IllegalArgumentException("Invalid response format");
        }
    }

    @Override
    public String getProviderId() {
        return Optional.ofNullable(attribute.get("id"))
                .map(Object::toString)
                .orElse("Unknown");
    }

    @Override
    public String getProvider() {
        return "Naver";
    }

    @Override
    public String getEmail() {
        return Optional.ofNullable(attribute.get("email"))
                .map(Object::toString)
                .orElse("No email provided");
    }

    @Override
    public String getUserName() {
        return Optional.ofNullable(attribute.get("name"))
                .map(Object::toString)
                .orElse("No name provided");
    }

    @Override
    public String getImageUrl() {
        return Optional.ofNullable(attribute.get("image_url"))
                .map(Object::toString)
                .orElse("No mobile provided");
    }
}
