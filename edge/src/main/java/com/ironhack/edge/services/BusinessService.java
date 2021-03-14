package com.ironhack.edge.services;

import com.ironhack.edge.dtos.BusinessDto;
import com.ironhack.edge.feing.BusinessClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessService {

    private final BusinessClient businessClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    public BusinessService(
            BusinessClient businessClient,
            CircuitBreakerFactory circuitBreakerFactory
    ){
        this.businessClient = businessClient;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public List<BusinessDto> findBusinessAll() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("business-ms");
        List<BusinessDto> businessDtos = circuitBreaker.run(
                () -> businessClient.findAll(), throwable -> FallbackBusinessAll());
        return businessDtos;
    }

    private List<BusinessDto> FallbackBusinessAll() {
        return new ArrayList<>();
    }
}



