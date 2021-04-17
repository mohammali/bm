package com.mohammali.web.bm.modules.statement.model;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class StatementIntoDto {

    Long id;

    Long accountId;

    String accountNumber;

    LocalDate date;

    BigDecimal amount;
}
