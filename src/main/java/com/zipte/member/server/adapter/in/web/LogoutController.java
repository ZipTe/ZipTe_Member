package com.zipte.member.server.adapter.in.web;

import com.zipte.member.core.response.ApiResponse;
import com.zipte.member.server.application.in.auth.LogoutUserUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    private final LogoutUserUseCase looutService;

    public LogoutController(LogoutUserUseCase looutService) {
        this.looutService = looutService;
    }

    @PostMapping
    public ApiResponse<String> logout(
            HttpServletRequest request, HttpServletResponse response) {
        looutService.logout(request, response);

        return ApiResponse.ok("로그 아웃 되었습니다.");
    }

}
