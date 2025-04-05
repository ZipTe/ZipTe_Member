package com.zipte.member.security.oauth2.domain.kakao;

import com.zipte.member.security.oauth2.domain.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class KakaoUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();  // 카카오의 기본 ID
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return kakaoAccount != null ? kakaoAccount.get("email").toString() : null;
    }

    @Override
    public String getUserName() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if (kakaoAccount != null) {
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            return profile != null ? profile.get("nickname").toString() : null;
        }
        return null;
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if (kakaoAccount != null) {
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            return profile != null ? profile.get("profile_image_url").toString() : null;
        }
        return null;
    }

    public String getSocialId() {
        return getProviderId(); // 카카오에서는 id가 social_id 역할을 함
    }

    public String getName() {
        return getUserName(); // 닉네임을 Name으로 매핑
    }
}
