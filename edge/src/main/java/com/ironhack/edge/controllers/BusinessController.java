package com.ironhack.edge.controllers;

import com.ironhack.edge.dtos.BusinessDto;
import com.ironhack.edge.services.BusinessService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping
    @ApiOperation(value = "Get all Business", notes = "return all Business")
    public List<BusinessDto> findBusinessAll() {
        return this.businessService.findBusinessAll();
    }


}

