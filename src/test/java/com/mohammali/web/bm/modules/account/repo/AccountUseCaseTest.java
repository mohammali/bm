package com.mohammali.web.bm.modules.account.repo;

import com.mohammali.web.bm.helper.LocalTest;
import com.mohammali.web.bm.modules.account.model.AccountEntity;
import com.mohammali.web.bm.modules.account.model.AccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Optional;

@LocalTest
class AccountUseCaseTest {

    private final long id = 1;
    @MockBean
    private AccountRepo accountRepo;
    @Autowired
    private AccountUseCase testSubject;

    @BeforeEach
    void init() {
        var account = new AccountEntity();
        account.setId(id);
        account.setAccountType(AccountType.SAVINGS);
        account.setAccountNumber(id + "");
        Mockito.when(accountRepo.findById(account.getId())).thenReturn(Optional.of(account));
        Mockito.when(accountRepo.findByAccountNumber(account.getAccountNumber())).thenReturn(Optional.of(account));
        Mockito.when(accountRepo.findAll()).thenReturn(Collections.singletonList(account));
    }

    @Test
    void whenFindByIdCallWithExistIdThenReturnAccount() {
        var result = testSubject.findById(id);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    void whenFindByIdCallWithNonExistIdThenReturnEmpty() {
        var result = testSubject.findById(0L);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void whenFindByAccountNumberCallWithExistAccountNumberThenReturnAccount() {
        var result = testSubject.findByAccountNumber(id + "");
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    void whenFindByAccountNumberCallWithExistAccountNumberThenReturnEmpty() {
        var result = testSubject.findByAccountNumber("");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void whenFindAllCallThenReturnNonEmptyList() {
        var result = testSubject.findAll();
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void checkIfTheAccountNumberIsHashed() {
        var result = testSubject.findById(id);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertTrue(result.get().getAccountNumber().startsWith("###"));
    }
}
