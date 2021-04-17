package com.mohammali.web.bm.modules.statement.data;

import com.mohammali.web.bm.modules.statement.model.StatementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface StatementJpaRepo extends JpaRepository<StatementEntity, Long> {

    List<StatementEntity> findAllByAccountId(Long accountId);
}
