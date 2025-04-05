package com.zipte.member.security.oauth2.handler;

import com.zipte.member.security.jwt.provider.JwtTokenProvider;
import com.zipte.member.security.jwt.service.JwtTokenService;
import com.zipte.member.security.oauth2.domain.PrincipalDetails;
import com.zipte.member.server.domain.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenService tokenService;
    /*
        토큰 발급을 진행합니다.
     */

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();

        String accessToken = tokenService.createAccessToken(user);
        log.info("회원가입 되었습니다!");

        String targetUrl = determineTargetUrl(request, response, authentication);

        // 생성한 URL로 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
