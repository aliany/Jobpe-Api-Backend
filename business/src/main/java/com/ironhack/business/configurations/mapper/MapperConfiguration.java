package com.ironhack.business.configurations.mapper;

import com.ironhack.business.dtos.OfferDto;
import com.ironhack.business.models.Offer;
import com.ironhack.business.utils.MoneyUtils;
import org.javamoney.moneta.Money;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public CustomMapper modelMapper() {
        CustomMapper modelMapper = new CustomMapper();
        modelMapper
                .getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper
                .getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());
        Condition notNull = ctx -> ctx.getSource() != null;
        Condition isNull = ctx -> ctx.getSource() == null;

        //OFFER
        modelMapper
                .typeMap(OfferDto.class, Offer.class)
                .addMappings(mapper -> mapper.using(DoubleToMoney).map(OfferDto::getSalaryAmountMin, Offer::setSalaryAmountMin))
                .addMappings(mapper -> mapper.using(DoubleToMoney).map(OfferDto::getSalaryAmountMax, Offer::setSalaryAmountMax));
        modelMapper
                .typeMap(Offer.class, OfferDto.class)
                .addMappings(mapper -> mapper.using(MoneyToString).map(Offer::getSalaryAmountMin, OfferDto::setSalaryAmountMinFormatted))
                .addMappings(mapper -> mapper.using(MoneyToString).map(Offer::getSalaryAmountMax, OfferDto::setSalaryAmountMaxFormatted))
                .addMappings(mapper -> mapper.using(MoneyToDouble).map(Offer::getSalaryAmountMin, OfferDto::setSalaryAmountMin))
                .addMappings(mapper -> mapper.using(MoneyToDouble).map(Offer::getSalaryAmountMax, OfferDto::setSalaryAmountMax));

        return modelMapper;
    }

    Converter<Double, Money> DoubleToMoney = new AbstractConverter<Double, Money>() {
        protected Money convert(Double source) {
            return Money.of(source, MoneyUtils.getCurrencyUnit());
        }
    };

    Converter<Money, Double> MoneyToDouble = new AbstractConverter<Money, Double>() {
        protected Double convert(Money source) {
            return source.getNumber().doubleValue();
        }
    };

    Converter<Money, String> MoneyToString = new AbstractConverter<Money, String>() {
        protected String convert(Money source) {
            return source.toString();
        }
    };
}
