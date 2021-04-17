package com.mohammali.web.bm.modules.statement.data;

import com.mohammali.web.bm.common.exceptions.AccountNotFoundException;
import com.mohammali.web.bm.common.exceptions.NotFoundException;
import com.mohammali.web.bm.common.exceptions.NotValidRangeException;
import com.mohammali.web.bm.common.model.AmountRangeDto;
import com.mohammali.web.bm.common.model.DateRangeDto;
import com.mohammali.web.bm.helper.LocalTest;
import com.mohammali.web.bm.modules.account.model.AccountInfoDto;
import com.mohammali.web.bm.modules.account.model.AccountType;
import com.mohammali.web.bm.modules.account.repo.AccountUseCase;
import com.mohammali.web.bm.modules.statement.model.StatementEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@LocalTest
class StatementUseCaseTest {

    private final AccountInfoDto account1 = new AccountInfoDto(1L, "1", AccountType.SAVINGS);
    private final AccountInfoDto account2 = new AccountInfoDto(2L, "2", AccountType.CURRENT);
    @MockBean
    private AccountUseCase accountUseCase;
    @MockBean
    private StatementRepo statementRepo;
    @Autowired
    private StatementUseCase testSubject;

    @BeforeEach
    void init() {
        initAccounts();
        initStatements();
    }

    @Test
    void whenFindAllCallThenReturnAllStatementsList() {
        var result = testSubject.findAll();
        Assertions.assertEquals(6, result.size());
    }

    @Test
    void whenQueryCallWithValidAccountThenReturnStatementList() throws NotFoundException, NotValidRangeException {
        var result = testSubject.query(account1.getAccountNumber(), null, null);
        Assertions.assertEquals(2, result.size());
        result = testSubject.query(account2.getAccountNumber(), null, null);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void whenQueryCallWithNonValidAccountThenThrowAccountNotFoundException() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            testSubject.query("", null, null);
        });
    }

    @Test()
    void whenQueryCallWithValidAccountAndNonValidDateRangeThenThrowNotValidRangeExceptionException() {
        Assertions.assertThrows(NotValidRangeException.class, () -> {
            testSubject.query(
                account2.getAccountNumber(),
                null,
                new DateRangeDto(LocalDate.now(), LocalDate.now().minusDays(3))
            );
        });
    }

    @Test
    void whenQueryCallWithValidAccountValidDateRangeThenReturnValidStatementList() throws NotFoundException, NotValidRangeException {
        var result = testSubject.query(account1.getAccountNumber(), null, new DateRangeDto(LocalDate.now().minusYears(1), LocalDate.now()));
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void whenQueryCallWithValidAccountValidEdgeDateRangeThenReturnValidStatementList() throws NotFoundException, NotValidRangeException {
        var result = testSubject.query(account1.getAccountNumber(), null, new DateRangeDto(LocalDate.now().minusMonths(4), LocalDate.now()));
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void whenQueryCallWithValidAccountValidAmountRangeThenReturnValidStatementList() throws NotFoundException, NotValidRangeException {
        var result = testSubject.query(account1.getAccountNumber(), new AmountRangeDto(new BigDecimal("100"), new BigDecimal("150")), null);
        Assertions.assertEquals(2, result.size());
    }

    private void initAccounts() {
        Mockito.when(accountUseCase.findById(account1.getId())).thenReturn(Optional.of(account1));
        Mockito.when(accountUseCase.findByAccountNumber(account1.getAccountNumber())).thenReturn(Optional.of(account1));

        Mockito.when(accountUseCase.findById(account2.getId())).thenReturn(Optional.of(account2));
        Mockito.when(accountUseCase.findByAccountNumber(account2.getAccountNumber())).thenReturn(Optional.of(account2));

        Mockito.when(accountUseCase.findAll()).thenReturn(Arrays.asList(account1, account2));
    }

    private void initStatements() {
        var list1 = createStatementsForAccount(account1);

        Mockito.when(statementRepo.findAllByAccountId(account1.getId())).thenReturn(list1);

        var list2 = createStatementsForAccount(account2);

        Mockito.when(statementRepo.findAllByAccountId(account2.getId())).thenReturn(list2);

        var all = new ArrayList<StatementEntity>();
        all.addAll(list1);
        all.addAll(list2);
        Mockito.when(statementRepo.findAll()).thenReturn(all);
    }

    private List<StatementEntity> createStatementsForAccount(AccountInfoDto account) {
        var account1Statement1 = new StatementEntity();
        account1Statement1.setId(new Random().nextLong());
        account1Statement1.setAccountId(account.getId());
        account1Statement1.setAmount(new BigDecimal("100.0"));
        account1Statement1.setDateField(LocalDate.now());

        var account1Statement2 = new StatementEntity();
        account1Statement2.setId(new Random().nextLong());
        account1Statement2.setAccountId(account.getId());
        account1Statement2.setAmount(new BigDecimal("150.0"));
        account1Statement2.setDateField(LocalDate.now().minusDays(1));

        var account1Statement3 = new StatementEntity();
        account1Statement3.setId(new Random().nextLong());
        account1Statement3.setAccountId(account.getId());
        account1Statement3.setAmount(new BigDecimal("200.0"));
        account1Statement3.setDateField(LocalDate.now().minusMonths(4));

        return Arrays.asList(account1Statement1, account1Statement2, account1Statement3);
    }
}
