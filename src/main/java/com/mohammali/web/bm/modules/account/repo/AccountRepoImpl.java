package com.mohammali.web.bm.modules.account.repo;

import com.mohammali.web.bm.modules.account.model.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
final class AccountRepoImpl implements AccountRepo {

    private final AccountJpaRepo jpaRepo;

    @Override
    public List<AccountEntity> findAll() {
        return jpaRepo.findAll();
    }

    @Override
    public Optional<AccountEntity> findById(Long id) {
        return jpaRepo.findById(id);
    }

    @Override
    public Optional<AccountEntity> findByAccountNumber(String accountNumber) {
        return jpaRepo.findByAccountNumber(accountNumber);
    }
}
