package com.mohammali.web.bm.modules.user.data;

import com.mohammali.web.bm.modules.user.model.UserEntity;

import java.util.Optional;

public interface UserUseCase {

    Optional<UserEntity> findByUsername(String username);
}
