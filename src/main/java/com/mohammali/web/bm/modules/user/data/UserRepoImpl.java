package com.mohammali.web.bm.modules.user.data;

import com.mohammali.web.bm.modules.user.model.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
final class UserRepoImpl implements UserRepo {

    private final List<UserEntity> list;

    public UserRepoImpl(PasswordEncoder passwordEncoder) {
        list = Arrays.asList(
            new UserEntity(
                1L,
                "Admin",
                "admin",
                passwordEncoder.encode("admin"),
                Collections.singletonList("ADMIN")
            ),
            new UserEntity(
                2L,
                "User",
                "user",
                passwordEncoder.encode("user"),
                Collections.singletonList("USER")
            )
        );
    }

    @Override
    public List<UserEntity> findAll() {
        return list;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return list.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return list.stream().filter(user -> user.getUsername().equalsIgnoreCase(username)).findFirst();
    }
}
