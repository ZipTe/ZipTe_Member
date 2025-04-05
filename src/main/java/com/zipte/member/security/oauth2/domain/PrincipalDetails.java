package com.zipte.member.security.oauth2.domain;

import com.zipte.member.server.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Builder
@RequiredArgsConstructor
public class PrincipalDetails implements OAuth2User{

    private final User user;
    private final Map<String, Object> attributes;

    // 생성자
    public static PrincipalDetails of(User user, Map<String, Object> attributes) {
        return PrincipalDetails.builder()
                .user(user)
                .attributes(attributes)
                .build();
    }

    /// 로직
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(o -> new SimpleGrantedAuthority(
                o.getRole()
        )).collect(Collectors.toList());
    }


    @Override
    public String getName() {
        return user.getEmail();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public Long getId() {
        return user.getId();
    }

    public String getEmail() {
        return user.getEmail();
    }

}
