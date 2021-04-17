package com.mohammali.web.bm.modules.statement.data;

import com.mohammali.web.bm.modules.statement.model.StatementEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
final class StatementRepoImpl implements StatementRepo {

    private final StatementJpaRepo jpaRepo;

    @Override
    public List<StatementEntity> findAll() {
        return jpaRepo.findAll();
    }

    @Override
    public Optional<StatementEntity> findById(Long id) {
        return jpaRepo.findById(id);
    }

    @Override
    public List<StatementEntity> findAllByAccountId(Long accountId) {
        return jpaRepo.findAllByAccountId(accountId);
    }
}
