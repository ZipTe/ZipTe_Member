package com.zipte.member.core.config;

import com.zipte.member.security.jwt.handler.JwtAuthenticationFilter;
import com.zipte.member.security.jwt.util.RequestMatcherHolder;
import com.zipte.member.security.oauth2.handler.OAuth2FailureHandler;
import com.zipte.member.security.oauth2.handler.OAuth2SuccessHandler;
import com.zipte.member.security.oauth2.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.zipte.member.server.domain.user.UserRole.ADMIN;
import static com.zipte.member.server.domain.user.UserRole.MEMBER;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final RequestMatcherHolder requestMatcherHolder;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/login?error", "/error", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // rest api 설정
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화 -> cookie를 사용하지 않으면 꺼도 된다. (cookie를 사용할 경우 httpOnly(XSS 방어), sameSite(CSRF 방어)로 방어해야 한다.)
                .httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 로그인 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 기본 login form 비활성화
                .logout(AbstractHttpConfigurer::disable) // 기본 logout 비활성화
                .headers(c -> c.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::disable).disable()) // X-Frame-Options 비활성화
                .sessionManagement(c ->
                        c.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // 세션 필요한 경우 사용

                // request 인증, 인가 설정
                .authorizeHttpRequests(request -> request
                        .requestMatchers(requestMatcherHolder.getRequestMatchersByMinRole(null))
                            .permitAll()
                        .requestMatchers(requestMatcherHolder.getRequestMatchersByMinRole(MEMBER))
                            .hasAnyAuthority(MEMBER.getRole(), ADMIN.getRole())
                        .requestMatchers(requestMatcherHolder.getRequestMatchersByMinRole(ADMIN))
                            .hasAnyAuthority(ADMIN.getRole())
                        .anyRequest().permitAll()
                )

                // oauth2 설정
                .oauth2Login(oauth -> // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                        // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
                        oauth.userInfoEndpoint(c -> c.userService(oAuth2UserService))
                                // 로그인 성공 시 핸들러
                                .successHandler(oAuth2SuccessHandler)
                                .failureHandler(oAuth2FailureHandler))

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

        ;

        return http.build();
    }

}
