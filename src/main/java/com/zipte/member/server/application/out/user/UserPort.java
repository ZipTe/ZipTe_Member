package com.zipte.member.server.application.out.user;


import com.zipte.member.server.domain.user.User;

import java.util.Optional;

public interface UserPort {

    /// 저장
    User saveUser(User user);

    // 수정하기
    User updateUser(User user);

    /// 조회하기
    boolean loadExistingEmail(String email);

    Optional<User> loadUserById(Long userId);

    Optional<User> loadUserByEmail(String email);
}
