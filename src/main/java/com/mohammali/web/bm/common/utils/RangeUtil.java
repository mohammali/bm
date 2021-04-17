package com.mohammali.web.bm.common.utils;

import com.mohammali.web.bm.common.exceptions.NotValidAmountRangeException;
import com.mohammali.web.bm.common.exceptions.NotValidDateRangeException;
import com.mohammali.web.bm.common.model.AmountRangeDto;
import com.mohammali.web.bm.common.model.DateRangeDto;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public final class RangeUtil {

    private RangeUtil() {
    }

    @Nullable
    public static AmountRangeDto validateAmountRangeDto(AmountRangeDto amountRange) throws NotValidAmountRangeException {
        if (amountRange != null) {
            if (amountRange.getFrom() == null && amountRange.getTo() == null) {
                return null;
            }

            if (amountRange.getFrom() == null || amountRange.getTo() == null) {
                throw new NotValidAmountRangeException();
            }

            if (amountRange.getFrom().compareTo(amountRange.getTo()) > 0) {
                throw new NotValidAmountRangeException();
            }
        }

        return amountRange;
    }

    @NonNull
    public static DateRangeDto validateDateRange(DateRangeDto dateRange) throws NotValidDateRangeException {
        if (dateRange != null && dateRange.getFrom() != null && dateRange.getTo() != null) {
            if (dateRange.getFrom().compareTo(dateRange.getTo()) > 0) {
                throw new NotValidDateRangeException();
            }
            return dateRange;
        }
        return new DateRangeDto(LocalDate.now().minusMonths(3), LocalDate.now());
    }
}
