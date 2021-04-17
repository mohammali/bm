package com.mohammali.web.bm.modules.statement.data;

import com.mohammali.web.bm.common.data.BaseRepo;
import com.mohammali.web.bm.modules.statement.model.StatementEntity;

import java.util.List;

interface StatementRepo extends BaseRepo<StatementEntity, Long> {

    List<StatementEntity> findAllByAccountId(Long accountId);
}
