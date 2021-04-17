package com.mohammali.web.bm.modules.account.repo;

import com.mohammali.web.bm.modules.account.model.AccountInfoDto;

import java.util.List;
import java.util.Optional;

public interface AccountUseCase {

    Optional<AccountInfoDto> findById(Long id);

    Optional<AccountInfoDto> findByAccountNumber(String accountNumber);

    List<AccountInfoDto> findAll();
}
