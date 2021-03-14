package com.ironhack.business.controllers;

import com.ironhack.business.dtos.BusinessDto;
import com.ironhack.business.services.BusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.websocket.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "business")
@Api(value = "Business´s Requests")
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping
    @ApiOperation(value = "Get all Business", notes = "return all Business")
    public List<BusinessDto> findAll(
            HttpServletRequest request
    ) {
        businessService.setToken(request.getHeader(Constants.AUTHORIZATION_HEADER_NAME));
        return this.businessService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Search one Business", notes = "return one Business")
    public BusinessDto findById(@PathVariable Long id) {
        return this.businessService.findById(id);
    }

    @PostMapping
    @ApiOperation(value = "Create one Business", notes = "return one Business")
    @ResponseStatus(HttpStatus.CREATED)
    public BusinessDto create(
            @Valid @RequestBody BusinessDto businessDto
    ) {
        return this.businessService.create(businessDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Create one Business", notes = "return one Business")
    @ResponseStatus(HttpStatus.OK)
    public BusinessDto update(
            @PathVariable Long id,
            @Valid @RequestBody BusinessDto businessDto
    ) {
        return this.businessService.update(businessDto, id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete one Business", notes = "Business deleted")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        this.businessService.delete(id);
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "Search one Business", notes = "return one Business")
    public BusinessDto findByUserId(@PathVariable Long id) {
        return this.businessService.findByUserId(id);
    }

}
