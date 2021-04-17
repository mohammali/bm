package com.mohammali.web.bm.modules.statement.model;

import com.mohammali.web.bm.common.db.converter.DateConverter;
import com.mohammali.web.bm.common.db.converter.MoneyConverter;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "statement")
public class StatementEntity {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Convert(converter = DateConverter.class)
    @Column(name = "datefield")
    private LocalDate dateField;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "amount")
    private BigDecimal amount;
}
