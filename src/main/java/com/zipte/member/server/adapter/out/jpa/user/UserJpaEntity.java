package com.zipte.member.server.adapter.out.jpa.user;

import com.zipte.member.server.domain.user.OAuthProvider;
import com.zipte.member.server.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    private String nickname;
    private String imageUrl;
    private OAuthProvider social;
    private String description;
    private String birthday;

    // from
    public static UserJpaEntity from(User user) {
        return UserJpaEntity.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .social(user.getSocial())
                .description(user.getDescription())
                .birthday(user.getBirthday())
                .build();
    }

    // toDomain
    public User toDomain(){
        return User.builder()
                .id(this.id)
                .email(this.email)
                .username(this.username)
                .nickname(this.nickname)
                .imageUrl(this.imageUrl)
                .social(this.social)
                .description(this.description)
                .birthday(this.birthday)
                .build();

    }
}
