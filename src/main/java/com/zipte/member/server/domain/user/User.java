package com.zipte.member.server.domain.user;

import com.zipte.member.server.domain.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends BaseDomain {

    private Long id;

    private String email;

    private String username;

    private String nickname;

    private String birthday;

    private String imageUrl;

    private OAuthProvider social;

    private String description;

    private List<UserRole> roles;

    private UserConsent consent;


    /// 정적 팩토리 메소드 생성자
    public static User of(String email, String username, String nickname, String imageUrl, String description, String birthday, OAuthProvider social) {
        return User.builder()
                .email(email)
                .username(username)
                .nickname(nickname)
                .imageUrl(imageUrl)
                .social(social)
                .description(description)
                .birthday(birthday)
                .build();
    }

    /// 비즈니스 로직

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeBirthday(String birthday) {
        this.birthday = birthday;
    }


}
