package com.mohammali.web.bm.common.db.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Converter
public class DateConverter implements AttributeConverter<LocalDate, String> {

    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Override
    public String convertToDatabaseColumn(LocalDate localDate) {
        return localDate.format(DATE_FORMATTER);
    }

    @Override
    public LocalDate convertToEntityAttribute(String s) {
        return LocalDate.parse(s, DATE_FORMATTER);
    }
}
