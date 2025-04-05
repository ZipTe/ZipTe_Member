package com.zipte.member.server.adapter.in.web;

import com.zipte.member.core.response.ApiResponse;
import com.zipte.member.server.adapter.in.web.dto.request.UserUpdateRequest;
import com.zipte.member.server.adapter.in.web.dto.response.UserResponse;
import com.zipte.member.server.application.in.user.GetUserUseCase;
import com.zipte.member.server.application.in.user.UpdateUserUseCase;
import com.zipte.member.server.domain.user.User;
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

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getMyInfo(@PathVariable Long userId) {
        User user = getService.getMyInfo(userId);

        return ApiResponse.ok(UserResponse.from(user));
    }

    @PutMapping("/{userId}")
    public ApiResponse<String> updateMyInfo(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {

        updateService.updateUser(userId, request);
        return ApiResponse.ok("수정되었습니다.");
    }

}
