package com.mohammali.web.bm.modules.account.model;

import com.mohammali.web.bm.common.db.converter.AccountTypeConverter;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @Column(name = "ID")
    private Long id;

    @Convert(converter = AccountTypeConverter.class)
    @Column(name = "account_type")
    private AccountType accountType;

    @Column(name = "account_number")
    private String accountNumber;
}
