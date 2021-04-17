package com.mohammali.web.bm.modules.user.data;

import com.mohammali.web.bm.common.data.BaseRepo;
import com.mohammali.web.bm.modules.user.model.UserEntity;

import java.util.Optional;

interface UserRepo extends BaseRepo<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}
