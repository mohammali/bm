package com.mohammali.web.bm.common.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class AmountRangeDto {

    BigDecimal from;

    BigDecimal to;
}
