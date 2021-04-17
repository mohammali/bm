package com.mohammali.web.bm.modules.account.model;

import lombok.Value;

@Value
public class AccountInfoDto {

    Long id;

    String accountNumber;

    AccountType type;
}
