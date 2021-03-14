package com.ironhack.business.controllers;

import com.ironhack.business.dtos.InscriptionDto;
import com.ironhack.business.enums.InscriptionsStatus;
import com.ironhack.business.services.InscriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.websocket.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "inscription")
@Api(value = "Inscription´s Requests")
public class InscriptionController {

    private final InscriptionService inscriptionService;

    @Autowired
    public InscriptionController(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Search one Business", notes = "return one Business")
    public InscriptionDto findById(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        inscriptionService.setToken(request.getHeader(Constants.AUTHORIZATION_HEADER_NAME));
        return this.inscriptionService.findById(id);
    }


    @PostMapping("/enrolled")
    @ApiOperation(value = "Create one inscription", notes = "return one inscription")
    @ResponseStatus(HttpStatus.CREATED)
    public InscriptionDto enrolled(
            @Valid @RequestBody InscriptionDto inscriptionDto
    ) {
        return this.inscriptionService.save(inscriptionDto, null, null);
    }

    @PostMapping("/accepted/{id}")
    @ApiOperation(value = "Create one inscription", notes = "return one inscription")
    @ResponseStatus(HttpStatus.OK)
    public InscriptionDto accepted(
            @PathVariable Long id
    ) {
        return this.inscriptionService.save(null, id, InscriptionsStatus.ACCEPTED);
    }

    @PostMapping("/rejected/{id}")
    @ApiOperation(value = "Create one inscription", notes = "return one inscription")
    @ResponseStatus(HttpStatus.OK)
    public InscriptionDto rejected(
            @PathVariable Long id
    ) {
        return this.inscriptionService.save(null, id, InscriptionsStatus.REJECTED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete one Inscription", notes = "Inscription deleted")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        this.inscriptionService.delete(id);
    }

    @PostMapping("/unenrolled")
    @ApiOperation(value = "Create one inscription", notes = "return one inscription")
    @ResponseStatus(HttpStatus.OK)
    public void unenrolled(
            @Valid @RequestBody InscriptionDto inscriptionDto
    ) {
        this.inscriptionService.unenrolled(inscriptionDto);
    }

}
