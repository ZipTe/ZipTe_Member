package com.zipte.member.security.jwt.provider;

import com.zipte.member.security.jwt.provider.exception.CustomJWTException;
import com.zipte.member.security.oauth2.domain.PrincipalDetails;
import com.zipte.member.server.application.out.user.UserPort;
import com.zipte.member.server.domain.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;

import java.util.*;
import java.util.Date;

import static com.zipte.member.security.jwt.util.JwtTokenKey.ACCESS_TOKEN_EXPIRATION_TIME;
import static com.zipte.member.security.jwt.util.JwtTokenKey.REFRESH_TOKEN_EXPIRATION_TIME;
import static org.springframework.security.oauth2.core.OAuth2ErrorCodes.INVALID_TOKEN;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.key}")
    private String key;
    private SecretKey secretKey;
    private final UserPort userPort;

    @PostConstruct
    private void setSecretKey() {
        secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }

    // Access token 발급
    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    // Refresh token 발급
    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public String generateToken(Authentication authentication, long expireTime) {

        /// 시간 설정
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expireTime);

        /// 인증에서 객체 가져오기
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        /// 권한 리스트 추출
        Collection<? extends GrantedAuthority> collection = principalDetails.getAuthorities();

        // String 형태로 변환
        List<String> authorities = collection.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        /// JWT 내용 생성
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", principalDetails.getId());
        claims.put("roles", authorities);

        // 토큰 반환
        return Jwts.builder()
                .setSubject(String.valueOf(principalDetails.getId())) // 사용자 Id
                .setClaims(claims)
                .setIssuedAt(now)                                // 발급 시간
                .setExpiration(expiredDate)                      // 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS512)   // 서명
                .compact();
    }

    public Authentication getAuthentication(String token) {

        /// JWT 파싱
        Claims claims = parseClaims(token);

        /// 권한 정보 가져오기
        List<String> authoritiesList = (List<String>) claims.get("roles");

        // 권한을 SimpleGrantedAuthority로 변환
        Collection<? extends GrantedAuthority> authorities =
                authoritiesList.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();

        // userId를 Long으로 안전하게 변환
        Long userId = claims.get("user_id", Long.class);

        // 해당 userId로 Member를 조회
        User user = userPort.loadUserById(userId)
                .orElseThrow(() -> new CustomJWTException(INVALID_TOKEN));

        PrincipalDetails details = PrincipalDetails.of(user);

        /// SecurityContext에 저장하기 위한 UsernamePasswordAuthenticationToken 반환
        return new UsernamePasswordAuthenticationToken(details, token, authorities);
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        Claims claims = parseClaims(token);
        return claims.getExpiration().after(new Date());
    }

    /// 내부 함수
    private Claims parseClaims(String token) {
        try {
            // JWT 파서를 빌드하고 서명된 토큰을 파싱
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)  // 서명 키를 설정
                    .build()
                    .parseClaimsJws(token)  // 서명된 JWT 토큰을 파싱
                    .getBody();  // Claims 객체 반환
        } catch (ExpiredJwtException e) {
            // 만료된 JWT 토큰의 경우 만료된 Claims 반환
            return e.getClaims();
        } catch (MalformedJwtException e) {
            // 잘못된 JWT 토큰 형식일 경우 예외 처리
            throw new CustomJWTException(INVALID_TOKEN);
        }
    }

}
