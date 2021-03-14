package com.ironhack.users.configurations.mapper;

import org.modelmapper.Condition;
import org.modelmapper.Conditions;
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

        return modelMapper;
    }
}
