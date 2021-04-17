package com.mohammali.web.bm.modules.account.repo;

import com.mohammali.web.bm.common.data.BaseRepo;
import com.mohammali.web.bm.modules.account.model.AccountEntity;

import java.util.Optional;

interface AccountRepo extends BaseRepo<AccountEntity, Long> {

    Optional<AccountEntity> findByAccountNumber(String accountNumber);
}
