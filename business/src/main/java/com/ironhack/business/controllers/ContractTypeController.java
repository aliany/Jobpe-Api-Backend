package com.ironhack.business.controllers;

import com.ironhack.business.dtos.ContractTypeDto;
import com.ironhack.business.services.ContractTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "contract-type")
@Api(value = "Contract Type´s Requests")
public class ContractTypeController {

    private final ContractTypeService contractTypeService;

    @Autowired
    public ContractTypeController(
            ContractTypeService contractTypeService
    ) {
        this.contractTypeService = contractTypeService;
    }

    @GetMapping
    @ApiOperation(value = "Get all Contract Type", notes = "return all Contract Type")
    public List<ContractTypeDto> findAll() {
        return this.contractTypeService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Search one Contract Type", notes = "return one Contract Type")
    public ContractTypeDto findById(@PathVariable Long id) {
        return this.contractTypeService.findById(id);
    }


}
