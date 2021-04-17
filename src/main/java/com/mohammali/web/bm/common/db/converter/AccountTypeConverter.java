package com.mohammali.web.bm.common.db.converter;

import com.mohammali.web.bm.modules.account.model.AccountType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Locale;

@Converter
public class AccountTypeConverter implements AttributeConverter<AccountType, String> {

    @Override
    public String convertToDatabaseColumn(AccountType accountType) {
        return accountType.name().toLowerCase(Locale.ENGLISH);
    }

    @Override
    public AccountType convertToEntityAttribute(String str) {
        try {
            return AccountType.valueOf(str.toUpperCase());
        } catch (Exception e) {
            return AccountType.UNKNOWN;
        }
    }
}
