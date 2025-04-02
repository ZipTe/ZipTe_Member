package com.zipte.member.server.application.out.auth;


import com.zipte.member.server.adapter.out.oauth.OAuthUserInfo;

public interface AuthPort {

    /*
        카카오에 로그인 요청을 하는 Port 이다.
     */

    // 카카오 로그인 요청
    String requestAccessToken(OAuthLoginParams params);

    // 정보 요청
    OAuthUserInfo requestOAuthInfo(String accessToken);

}
