package com.mohammali.web.bm.modules.user.data;

import com.mohammali.web.bm.helper.LocalTest;
import com.mohammali.web.bm.modules.user.model.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Optional;

@LocalTest
class UserUseCaseTest {

    @MockBean
    private UserRepo userRepo;

    @Autowired
    private UserUseCase testSubject;

    @BeforeAll
    public void init() {
        var user = new UserEntity(
            1L,
            "test",
            "test",
            "test",
            Collections.emptyList()
        );
        Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
    }

    @Test
    void whenFindByUsernameCallWithNonExistUserThenReturnEmpty() {
        var result = testSubject.findByUsername("");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void whenFindByUsernameCallWithExistUserThenReturnUser() {
        var result = testSubject.findByUsername("test");
        Assertions.assertTrue(result.isPresent());
    }
}
