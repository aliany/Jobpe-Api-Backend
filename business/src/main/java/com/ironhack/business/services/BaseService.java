package com.ironhack.business.services;

import com.ironhack.business.configurations.mapper.CustomMapper;
import com.ironhack.business.dtos.*;
import com.ironhack.business.feign.UserClient;
import com.ironhack.business.models.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Setter
public class BaseService {

    @Autowired
    private CustomMapper modelMapper;
    @Autowired
    private UserClient userClient;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    private String token = "";

    //    Offers
    protected OfferDto toDto(Offer offer) {
        return modelMapper.map(offer, OfferDto.class);
    }

    protected Offer toEntity(OfferDto offerDto) {
        return modelMapper.map(offerDto, Offer.class);
    }

    //    Contract Type
    protected ContractTypeDto toDto(ContractType contractType) {
        return modelMapper.map(contractType, ContractTypeDto.class);
    }

    protected ContractType toEntity(ContractTypeDto contractTypeDto) {
        return modelMapper.map(contractTypeDto, ContractType.class);
    }

    //    Workday
    protected WorkdayDto toDto(Workday workday) {
        return modelMapper.map(workday, WorkdayDto.class);
    }

    protected Workday toEntity(WorkdayDto workdayDto) {
        return modelMapper.map(workdayDto, Workday.class);
    }

    //    Requested Professional Experience
    protected RequestedProfessionalExperienceDto toDto(RequestedProfessionalExperience requestedProfessionalExperience) {
        return modelMapper.map(requestedProfessionalExperience, RequestedProfessionalExperienceDto.class);
    }

    protected RequestedProfessionalExperience toEntity(RequestedProfessionalExperienceDto requestedProfessionalExperienceDto) {
        return modelMapper.map(requestedProfessionalExperienceDto, RequestedProfessionalExperience.class);
    }

    //    Years Of Experience
    protected YearsOfExperienceDto toDto(YearsOfExperience yearsOfExperience) {
        return modelMapper.map(yearsOfExperience, YearsOfExperienceDto.class);
    }

    protected YearsOfExperience toEntity(YearsOfExperienceDto yearsOfExperienceDto) {
        return modelMapper.map(yearsOfExperienceDto, YearsOfExperience.class);
    }

    //    Business
    protected BusinessDto toDto(Business business) {
        return modelMapper.map(business, BusinessDto.class);
    }

    protected Business toEntity(BusinessDto businessDto) {
        return modelMapper.map(businessDto, Business.class);
    }

    //    Inscription
    protected InscriptionDto toDto(Inscription inscription) {
        return modelMapper.map(inscription, InscriptionDto.class);
    }

    protected Inscription toEntity(InscriptionDto inscriptionDto) {
        return modelMapper.map(inscriptionDto, Inscription.class);
    }

    //OPENFEIGN CALLS
    protected List<UserDto> findAllUsers() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("ms-users");
        return circuitBreaker.run(() -> this.userClient.findAllUsers(this.token));
    }

    protected UserDto findUser(Long id) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("ms-users");
        return circuitBreaker.run(() -> this.userClient.findUser(this.token, id));
    }

}
