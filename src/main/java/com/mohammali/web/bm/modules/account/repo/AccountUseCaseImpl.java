package com.mohammali.web.bm.modules.account.repo;

import com.mohammali.web.bm.modules.account.model.AccountEntity;
import com.mohammali.web.bm.modules.account.model.AccountInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
final class AccountUseCaseImpl implements AccountUseCase {

    private final AccountRepo repo;

    @Override
    public Optional<AccountInfoDto> findById(Long id) {
        return repo.findById(id).map(this::toAccountInfoDto);
    }

    @Override
    public Optional<AccountInfoDto> findByAccountNumber(String accountNumber) {
        return repo.findByAccountNumber(accountNumber).map(this::toAccountInfoDto);
    }

    @Override
    public List<AccountInfoDto> findAll() {
        return repo.findAll().stream().map(this::toAccountInfoDto).collect(Collectors.toList());
    }

    private AccountInfoDto toAccountInfoDto(AccountEntity entity) {
        return new AccountInfoDto(
            entity.getId(),
            hashAccountNumber(entity.getAccountNumber()),
            entity.getAccountType()
        );
    }

    private String hashAccountNumber(String accountNumber) {
        var result = "##########";
        if (accountNumber.length() > 3) {
            result += accountNumber.substring(accountNumber.length() - 3);
        } else {
            result += accountNumber;
        }
        return result;
    }
}
