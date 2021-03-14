package com.ironhack.business.configurations.converter;

import com.ironhack.business.utils.MoneyUtils;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;
import java.util.Locale;

@Converter
public class MoneyConverter implements AttributeConverter<Money, BigDecimal> {

    private static final CurrencyUnit currencyUnit = MoneyUtils.getCurrencyUnit();

    @Override
    public BigDecimal convertToDatabaseColumn(Money monetaryAmount) {
        return monetaryAmount.getNumberStripped();
    }

    @Override
    public Money convertToEntityAttribute(BigDecimal dbData) {
        return Money.of(dbData, currencyUnit);
    }
}
