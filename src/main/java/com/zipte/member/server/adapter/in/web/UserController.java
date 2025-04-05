package com.zipte.member.server.adapter.in.web;

import com.zipte.member.core.response.ApiResponse;
import com.zipte.member.security.oauth2.domain.PrincipalDetails;
import com.zipte.member.server.adapter.in.web.dto.request.UserUpdateRequest;
import com.zipte.member.server.adapter.in.web.dto.response.UserResponse;
import com.zipte.member.server.application.in.user.GetUserUseCase;
import com.zipte.member.server.application.in.user.UpdateUserUseCase;
import com.zipte.member.server.domain.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final GetUserUseCase getService;
    private final UpdateUserUseCase updateService;

    /// 생성자
    public UserController(GetUserUseCase getService, UpdateUserUseCase updateService) {
        this.getService = getService;
        this.updateService = updateService;
    }

    @GetMapping("/mypage")
    public ApiResponse<UserResponse> getMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = getService.getMyInfo(principalDetails.getId());

        return ApiResponse.ok(UserResponse.from(user));
    }

    @PutMapping()
    public ApiResponse<String> updateMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody UserUpdateRequest request) {

        updateService.updateUser(principalDetails.getId(), request);
        return ApiResponse.ok("수정되었습니다.");
    }

}
