package com.zipte.member.server.adapter;

import com.zipte.member.server.adapter.out.jpa.user.UserJpaRepository;
import com.zipte.member.server.application.out.user.UserPort;
import com.zipte.member.server.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPort {

    private final UserJpaRepository repository;

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public boolean loadExistingEmail(String email) {
        return false;
    }

    @Override
    public Optional<User> loadUserById(Long userId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> loadUserByEmail(String email) {
        return Optional.empty();
    }
}
