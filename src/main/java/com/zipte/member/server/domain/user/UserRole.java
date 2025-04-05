package com.zipte.member.server.domain.user;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    MEMBER("MEMBER"),
    ADMIN("ADMIN");

    private final String role;

    @JsonValue
    public String getRole() {
        return role;
    }
}
