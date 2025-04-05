package com.zipte.member.server.adapter.in.web.dto.request;

import com.zipte.member.server.domain.user.OAuthProvider;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequest {

    @NotNull(message = "email은 반드시 입력해야하는 필수 사항입니다!")
    @Email(message = "이메일 형식이 적합하지 않습니다")
    private String email;

    @NotNull(message = "username은 반드시 입력해야하는 필수 사항입니다!")
    @Size(max = 10, message = "이름은 최대 10글자 입니다")
    private String username;

    @NotNull(message = "nickname은 반드시 입력해야하는 필수 사항입니다!")
    @Size(max = 20, message = "닉네임은 최대 20글자 입니다.")
    private String nickname;

    private OAuthProvider provider;

    private String imageUrl;

    @NotNull(message = "description는 반드시 입력해야하는 필수 사항입니다!")
    @Size(max = 30, message = "한 줄 소개는 최대 30글자 입니다.")
    private String description;

    @NotNull(message = "birthday는 반드시 입력해야하는 필수 사항입니다!")
    // 3월 16일 이전의 경우는 입력이 안되도록 추가 설정
    private String birthday;
}
