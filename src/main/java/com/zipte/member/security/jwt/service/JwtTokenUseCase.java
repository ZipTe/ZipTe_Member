package com.zipte.member.security.jwt.service;

import com.zipte.member.server.domain.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface JwtTokenUseCase {

    // 액세스 토큰 생성하기
    String createAccessToken(User user);

    // 리프레쉬 토큰 생성하기
    String createRefreshToken(HttpServletResponse response, User user);

    // 재발급 하기
    String reissueByRefreshToken(HttpServletRequest request, HttpServletResponse response);

    // Refresh 토큰 제거하기
    void deleteRefreshToken(HttpServletRequest request, HttpServletResponse response);
}
