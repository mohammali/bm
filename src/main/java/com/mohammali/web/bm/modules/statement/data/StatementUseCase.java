package com.mohammali.web.bm.modules.statement.data;

import com.mohammali.web.bm.common.exceptions.NotFoundException;
import com.mohammali.web.bm.common.exceptions.NotValidRangeException;
import com.mohammali.web.bm.common.model.AmountRangeDto;
import com.mohammali.web.bm.common.model.DateRangeDto;
import com.mohammali.web.bm.modules.statement.model.StatementIntoDto;

import java.util.List;

public interface StatementUseCase {

    List<StatementIntoDto> query(
        String accountNumber,
        AmountRangeDto amountRange,
        DateRangeDto dateRange
    ) throws NotFoundException, NotValidRangeException;

    List<StatementIntoDto> findAll();
}
