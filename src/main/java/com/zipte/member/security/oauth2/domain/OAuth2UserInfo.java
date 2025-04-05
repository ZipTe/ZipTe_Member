package com.zipte.member.security.oauth2.domain;

import java.util.Map;

public interface OAuth2UserInfo {

    String getProvider();
    String getProviderId();
    String getEmail();
    String getUserName();
    String getImageUrl();
}
