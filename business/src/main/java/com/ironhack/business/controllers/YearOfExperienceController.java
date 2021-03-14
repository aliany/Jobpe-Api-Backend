package com.ironhack.business.controllers;

import com.ironhack.business.dtos.YearsOfExperienceDto;
import com.ironhack.business.services.YearOfExperienceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "year-of-experience")
@Api(value = "Year Of Experience´s Requests")
public class YearOfExperienceController {

    private final YearOfExperienceService yearOfExperienceService;

    @Autowired
    public YearOfExperienceController(
            YearOfExperienceService yearOfExperienceService
    ) {
        this.yearOfExperienceService = yearOfExperienceService;
    }

    @GetMapping
    @ApiOperation(value = "Get all Year Of Experience", notes = "return all Year Of Experience")
    public List<YearsOfExperienceDto> findAll() {
        return this.yearOfExperienceService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Search one Year Of Experience", notes = "return one Year Of Experience")
    public YearsOfExperienceDto findById(@PathVariable Long id) {
        return this.yearOfExperienceService.findById(id);
    }


}
