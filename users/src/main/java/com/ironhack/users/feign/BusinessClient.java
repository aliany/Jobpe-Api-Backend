package com.ironhack.users.feign;

import com.ironhack.users.dtos.BusinessDto;
import com.ironhack.users.dtos.RequestedProfessionalExperienceDto;
import com.ironhack.users.dtos.YearsOfExperienceDto;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@FeignClient("business-ms")
@LoadBalancerClient("business-ms")
public interface BusinessClient {

    @GetMapping("/business/user/{id}")
    BusinessDto findByUserId(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @PathVariable Long id);

    @GetMapping("/business")
    List<BusinessDto> findAll(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

    @PostMapping("/business")
    BusinessDto create(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @RequestBody @Valid BusinessDto businessDto);

    @GetMapping("/year-of-experience/{id}")
    YearsOfExperienceDto findYearsOfExperienceById(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @PathVariable Long id);

    @GetMapping("/year-of-experience")
    List<YearsOfExperienceDto> findAllYearsOfExperience(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

    @GetMapping("/requested-professional-experience/{id}")
    RequestedProfessionalExperienceDto findRequestedProfessionalExperienceById(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @PathVariable Long id);

    @GetMapping("/requested-professional-experience")
    List<RequestedProfessionalExperienceDto> findAllRequestedProfessionalExperience(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

}
