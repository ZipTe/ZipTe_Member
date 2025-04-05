package com.zipte.member.server.adapter.in.web;

import com.zipte.member.core.response.ApiResponse;
import com.zipte.member.security.jwt.service.JwtTokenUseCase;
import com.zipte.member.server.adapter.in.web.dto.request.AuthFirstTimeRequest;
import com.zipte.member.server.application.in.auth.AuthUserUseCase;
import com.zipte.member.server.domain.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class AuthController {

    // 토큰 재발급
    private final AuthUserUseCase authService;
    private final JwtTokenUseCase tokenService;


    @GetMapping("/signup")
    public String home() {
        return "체크 합니다.";
    }

    // 최초 로그인 시, 유저의 추가 정보 기입
    @PutMapping("/{email}")
    public ApiResponse<User> updateUser(
            @PathVariable String email,
            @RequestBody @Valid AuthFirstTimeRequest request) {
        User user = authService.updateInfo(email, request);

        return ApiResponse.ok(user);
    }

    // 토큰 재발급
    @PostMapping("/reissue")
    public ApiResponse<String> reissue(HttpServletRequest request, HttpServletResponse response) {
        String newAccessToken = tokenService.reissueByRefreshToken(request, response);

        return ApiResponse.ok(newAccessToken);
    }
}
