package com.zipte.member.server.application.service;

import com.zipte.member.security.jwt.provider.JwtTokenProvider;
import com.zipte.member.security.jwt.service.JwtTokenService;
import com.zipte.member.security.jwt.service.JwtTokenUseCase;
import com.zipte.member.server.application.in.auth.LogoutUserUseCase;
import com.zipte.member.server.application.out.user.UserPort;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogoutService implements LogoutUserUseCase {

    private final UserPort userPort;

    /// 토큰 제공
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenUseCase tokenService;


    /// 생성자
    public LogoutService(UserPort userPort, JwtTokenProvider jwtTokenProvider, JwtTokenService tokenService) {
        this.userPort = userPort;
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenService = tokenService;
    }


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        // 쿠키 및 DB에서 리프레시 토큰을 삭제
        tokenService.deleteRefreshToken(request, response);

    }
}
