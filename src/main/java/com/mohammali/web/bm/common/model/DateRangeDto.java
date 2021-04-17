package com.mohammali.web.bm.common.model;

import lombok.Value;

import java.time.LocalDate;

@Value
public class DateRangeDto {

    LocalDate from;

    LocalDate to;
}
