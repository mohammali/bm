package com.mohammali.web.bm.modules.statement.data;

import com.mohammali.web.bm.common.exceptions.AccountNotFoundException;
import com.mohammali.web.bm.common.exceptions.NotFoundException;
import com.mohammali.web.bm.common.exceptions.NotValidRangeException;
import com.mohammali.web.bm.common.model.AmountRangeDto;
import com.mohammali.web.bm.common.model.DateRangeDto;
import com.mohammali.web.bm.common.utils.RangeUtil;
import com.mohammali.web.bm.modules.account.model.AccountInfoDto;
import com.mohammali.web.bm.modules.account.repo.AccountUseCase;
import com.mohammali.web.bm.modules.statement.model.StatementEntity;
import com.mohammali.web.bm.modules.statement.model.StatementIntoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
final class StatementUseCaseImpl implements StatementUseCase {

    private final AccountUseCase accountUseCase;

    private final StatementRepo statementRepo;

    @Override
    public List<StatementIntoDto> query(
        String accountNumber,
        AmountRangeDto inAmountRange,
        DateRangeDto inDateRange
    ) throws NotFoundException, NotValidRangeException {
        var account = accountUseCase.findByAccountNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException(accountNumber));
        var dateRange = RangeUtil.validateDateRange(inDateRange);
        var amountRange = RangeUtil.validateAmountRangeDto(inAmountRange);
        return statementRepo.findAllByAccountId(account.getId())
            .stream()
            .filter(statementEntity ->
                statementEntity.getDateField().compareTo(dateRange.getFrom()) >= 0
                    && statementEntity.getDateField().compareTo(dateRange.getTo()) <= 0
            )
            .filter(statementEntity -> {
                if (amountRange != null) {
                    return statementEntity.getAmount().compareTo(amountRange.getFrom()) >= 0
                        && statementEntity.getAmount().compareTo(amountRange.getTo()) <= 0;
                } else {
                    return true;
                }
            })
            .map(statementEntity -> toStatementIntoDto(statementEntity, account))
            .sorted(Comparator.comparing(StatementIntoDto::getDate).reversed())
            .collect(Collectors.toList());
    }

    @Override
    public List<StatementIntoDto> findAll() {
        var accounts = accountUseCase.findAll()
            .stream()
            .collect(Collectors.toMap(AccountInfoDto::getId, Function.identity()));
        return statementRepo.findAll().stream()
            .map(statementEntity -> toStatementIntoDto(statementEntity, accounts.get(statementEntity.getAccountId())))
            .collect(Collectors.toList());
    }

    private StatementIntoDto toStatementIntoDto(StatementEntity statement, AccountInfoDto account) {
        return new StatementIntoDto(
            statement.getId(),
            statement.getAccountId(),
            account.getAccountNumber(),
            statement.getDateField(),
            statement.getAmount()
        );
    }
}
