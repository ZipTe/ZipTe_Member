package com.zipte.member.server.adapter.in.web.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zipte.member.server.domain.user.User;

@JsonPropertyOrder({"email", "accessToken", "firstLogin"})
public class UserLoginResponse {

    private final String email;
    private final String accessToken;
    private final boolean firstLogin;

    public UserLoginResponse(User user, String accessToken, boolean firstLogin) {
        this.email = user.getEmail();
        this.accessToken = accessToken;
        this.firstLogin = firstLogin;
    }

    public String getEmail() {
        return email;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public boolean isFirstLogin() {
        return firstLogin;
    }

}
