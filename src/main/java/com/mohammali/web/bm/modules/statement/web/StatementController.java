package com.mohammali.web.bm.modules.statement.web;

import com.mohammali.web.bm.common.exceptions.NotFoundException;
import com.mohammali.web.bm.common.exceptions.NotValidRangeException;
import com.mohammali.web.bm.common.model.AmountRangeDto;
import com.mohammali.web.bm.common.model.DateRangeDto;
import com.mohammali.web.bm.modules.statement.data.StatementUseCase;
import com.mohammali.web.bm.modules.statement.model.StatementIntoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api.statement")
@RequiredArgsConstructor
public class StatementController {

    private final StatementUseCase statementUseCase;

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') " +
        "&& #amountFrom == null " +
        "&& #amountTo == null " +
        "&& #dateFrom == null" +
        "&& #dateTo == null)")
    @GetMapping("query/accountNumber/{accountNumber}")
    public ResponseEntity<List<StatementIntoDto>> query(
        @PathVariable("accountNumber") String accountNumber,
        BigDecimal amountFrom,
        BigDecimal amountTo,
        LocalDate dateFrom,
        LocalDate dateTo
    ) {
        try {
            return ResponseEntity.ok(statementUseCase.query(
                accountNumber,
                new AmountRangeDto(amountFrom, amountTo),
                new DateRangeDto(dateFrom, dateTo)
            ));
        } catch (NotFoundException | NotValidRangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        }
    }
}
