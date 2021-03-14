package com.ironhack.business.controllers;

import com.ironhack.business.dtos.WorkdayDto;
import com.ironhack.business.services.WorkdayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "workday")
@Api(value = "Workday´s Requests")
public class WorkdayController {

    private final WorkdayService workdayService;

    @Autowired
    public WorkdayController(
            WorkdayService workdayService
    ) {
        this.workdayService = workdayService;
    }

    @GetMapping
    @ApiOperation(value = "Get all Workday", notes = "return all Workday")
    public List<WorkdayDto> findAll() {
        return this.workdayService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Search one Workday", notes = "return one Workday")
    public WorkdayDto findById(@PathVariable Long id) {
        return this.workdayService.findById(id);
    }


}
