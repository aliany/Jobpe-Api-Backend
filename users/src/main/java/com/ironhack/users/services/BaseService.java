package com.ironhack.users.services;


import com.ironhack.users.configurations.mapper.CustomMapper;
import com.ironhack.users.dtos.*;
import com.ironhack.users.feign.BusinessClient;
import com.ironhack.users.models.Profile;
import com.ironhack.users.models.User;
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
    private BusinessClient businessClient;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    private String token = "";

    //    Users
    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User toEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    //    Profiles
    public ProfileDto toDto(Profile profile) {
        return modelMapper.map(profile, ProfileDto.class);
    }

    public Profile toEntity(ProfileDto profileDto) {
        return modelMapper.map(profileDto, Profile.class);
    }


    //OPENFEIGN CALLS
    protected BusinessDto createBusiness(BusinessDto businessDto) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("ms-business");
        return circuitBreaker.run(() -> this.businessClient.create(this.token, businessDto));
    }

    protected List<BusinessDto> findAllBusiness() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("ms-business");
        return circuitBreaker.run(() -> this.businessClient.findAll(this.token));
    }

    public BusinessDto findByBusinessUserId(Long id) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("ms-business");
        return circuitBreaker.run(() -> this.businessClient.findByUserId(this.token, id));
    }

    public YearsOfExperienceDto findYearsOfExperienceById(Long id) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("ms-business");
        return circuitBreaker.run(() -> this.businessClient.findYearsOfExperienceById(this.token, id));
    }

    public RequestedProfessionalExperienceDto findRequestedProfessionalExperienceById(Long id) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("ms-business");
        return circuitBreaker.run(() -> this.businessClient.findRequestedProfessionalExperienceById(this.token, id));
    }
}
