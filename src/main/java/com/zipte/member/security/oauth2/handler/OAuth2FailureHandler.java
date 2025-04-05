package com.zipte.member.security.oauth2.handler;

import com.zipte.member.security.oauth2.handler.exception.RedirectToSignupException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Log4j2
@Component

public class OAuth2FailureHandler implements AuthenticationFailureHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        if (exception instanceof RedirectToSignupException) {

            // 회원가입 페이지로 리디렉션
            log.info("redirect to Signup");
            redirectStrategy.sendRedirect(request, response, "/signup");
        }
        else {
            response.sendRedirect("/login?error");
        }
    }
}
