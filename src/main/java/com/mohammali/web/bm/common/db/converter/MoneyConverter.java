package com.mohammali.web.bm.common.db.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;

@Converter
public class MoneyConverter implements AttributeConverter<BigDecimal, String> {

    @Override
    public String convertToDatabaseColumn(BigDecimal bigDecimal) {
        return bigDecimal.toPlainString();
    }

    @Override
    public BigDecimal convertToEntityAttribute(String s) {
        return new BigDecimal(s);
    }
}
