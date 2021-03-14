package com.ironhack.business.controllers;

import com.ironhack.business.dtos.RequestedProfessionalExperienceDto;
import com.ironhack.business.services.RequestedProfessionalExperienceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "requested-professional-experience")
@Api(value = "Requested Professional Experience´s Requests")
public class RequestedProfessionalExperienceController {

    private final RequestedProfessionalExperienceService requestedProfessionalExperienceService;

    @Autowired
    public RequestedProfessionalExperienceController(
            RequestedProfessionalExperienceService requestedProfessionalExperienceService
    ) {
        this.requestedProfessionalExperienceService = requestedProfessionalExperienceService;
    }

    @GetMapping
    @ApiOperation(value = "Get all Requested Professional Experience", notes = "return all Requested Professional Experience")
    public List<RequestedProfessionalExperienceDto> findAll() {
        return this.requestedProfessionalExperienceService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Search one Requested Professional Experience", notes = "return one Requested Professional Experience")
    public RequestedProfessionalExperienceDto findById(@PathVariable Long id) {
        return this.requestedProfessionalExperienceService.findById(id);
    }


}
