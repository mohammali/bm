package com.mohammali.web.bm.common.utils;

import com.mohammali.web.bm.common.exceptions.NotValidAmountRangeException;
import com.mohammali.web.bm.common.exceptions.NotValidDateRangeException;
import com.mohammali.web.bm.common.exceptions.NotValidRangeException;
import com.mohammali.web.bm.common.model.AmountRangeDto;
import com.mohammali.web.bm.common.model.DateRangeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

class RangeUtilTest {

    @Test
    void whenNotValidDateRangeThenThrowNotValidDateRangeException() {
        Assertions.assertThrows(NotValidDateRangeException.class, () -> {
            RangeUtil.validateDateRange(new DateRangeDto(LocalDate.now().plusDays(1), LocalDate.now()));
        });

        Assertions.assertThrows(NotValidDateRangeException.class, () -> {
            RangeUtil.validateDateRange(new DateRangeDto(LocalDate.now(), LocalDate.now().minusDays(1)));
        });
    }

    @Test
    void whenOneParamIsNullThenReturnDefaultRange() throws NotValidRangeException {
        var range = RangeUtil.validateDateRange(new DateRangeDto(null, LocalDate.now()));
        Assertions.assertEquals(3, Period.between(range.getFrom(), range.getTo()).getMonths());
    }

    @Test
    void whenNotValidAmountRangeThenThrowNotValidAmountRangeException() {
        Assertions.assertThrows(NotValidAmountRangeException.class, () -> {
            RangeUtil.validateAmountRangeDto(new AmountRangeDto(new BigDecimal("100.0"), new BigDecimal("40.0")));
        });
    }

    @Test
    void whenValidAmountRangeThenReturnValidRange() throws NotValidRangeException {
        var range = RangeUtil.validateAmountRangeDto(new AmountRangeDto(new BigDecimal("50.0"), new BigDecimal("100.0")));
        Assertions.assertNotNull(range);
        Assertions.assertEquals(new BigDecimal("50.0"), range.getTo().min(range.getFrom()));
    }
}
