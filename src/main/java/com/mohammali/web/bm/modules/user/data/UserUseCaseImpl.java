package com.mohammali.web.bm.modules.user.data;

import com.mohammali.web.bm.modules.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
final class UserUseCaseImpl implements UserUseCase {

    private final UserRepo repo;

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return repo.findByUsername(username);
    }
}
