package com.zipte.member.security.jwt.provider;

import com.zipte.member.server.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.zipte.member.security.jwt.util.JwtTokenKey.ACCESS_TOKEN_EXPIRATION_TIME;
import static com.zipte.member.security.jwt.util.JwtTokenKey.REFRESH_TOKEN_EXPIRATION_TIME;

/*
    JWT 부분은 GPT의 도움을 받았습니다 ...
 */

@Component
public class JwtTokenProvider {

    @Value("${jwt.key}")
    private String key;  // JWT를 서명할 비밀 키

    // JWT 생성 (AccessToken과 RefreshToken을 생성하기 위한 기본 메서드)
    public String createJwt(User user, long expirationTime) {
        long now = System.currentTimeMillis() / 1000;  // 현재 시간 (초 단위)
        long exp = now + expirationTime;  // 만료 시간 (파라미터로 받은 시간 추가)

        // 헤더 부분 (JSON)
        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String encodedHeader = Base64.getUrlEncoder().encodeToString(header.getBytes(StandardCharsets.UTF_8));

        // 페이로드 부분 (JSON)
        String payload = String.format("{\"sub\":\"%s\",\"id\":%d,\"iat\":%d,\"exp\":%d}", user.getEmail(), user.getId(), now, exp);
        String encodedPayload = Base64.getUrlEncoder().encodeToString(payload.getBytes(StandardCharsets.UTF_8));

        // 서명 부분 (HMAC SHA256을 사용하여 헤더와 페이로드를 서명)
        String signature = generateSignature(encodedHeader, encodedPayload);

        // 최종 JWT 토큰 생성
        return encodedHeader + "." + encodedPayload + "." + signature;
    }

    // Access Token 생성 (만료 시간: 1시간)
    public String createAccessToken(User user) {
        return createJwt(user, ACCESS_TOKEN_EXPIRATION_TIME);  // 상수로 설정된 만료 시간 사용
    }

    // Refresh Token 생성 (만료 시간: 7일)
    public String createRefreshToken(User user) {

        return createJwt(user, REFRESH_TOKEN_EXPIRATION_TIME);// 상수로 설정된 만료 시간 사용
    }

    // 서명 생성
    private String generateSignature(String encodedHeader, String encodedPayload) {
        try {
            String data = encodedHeader + "." + encodedPayload;
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");  // key로 서명 생성
            mac.init(secretKeySpec);

            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("서명 생성 실패", e);
        }
    }

    // JWT 유효성 검사 (서명을 체크)
    public boolean validateJwt(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return false;  // 토큰 형식이 잘못된 경우
        }

        String header = parts[0];
        String payload = parts[1];
        String signature = parts[2];

        String expectedSignature = generateSignature(header, payload);
        return expectedSignature.equals(signature);  // 서명이 일치하는지 확인
    }

    // JWT에서 이메일 추출 (페이로드에서)
    public String getEmailFromToken(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid token format");
        }

        String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
        String[] claims = payload.replace("{", "").replace("}", "").split(",");

        for (String claim : claims) {
            if (claim.startsWith("\"sub\":\"")) {
                return claim.split(":")[1].replace("\"", "");
            }
        }

        return null;
    }
}
