package com.zipte.member.security.jwt.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

public class JwtAuthenticationFailureFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // HTTP 401 (Unauthorized) 상태 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // JSON 형식으로 응답 반환
        PrintWriter writer = response.getWriter();
        writer.print("{\"message\": \"권한 에러입니다.\"}");
        writer.flush();
        writer.close();

        // 필터 체인 중단 (예외 발생 시 이후 필터 실행을 방지)
        return;
    }
}
