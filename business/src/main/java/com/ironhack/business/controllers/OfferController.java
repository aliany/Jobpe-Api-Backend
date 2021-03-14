package com.ironhack.business.controllers;

import com.ironhack.business.configurations.security.Constants;
import com.ironhack.business.configurations.security.jwt.JwtTokenUtil;
import com.ironhack.business.dtos.OfferDto;
import com.ironhack.business.enums.Rol;
import com.ironhack.business.services.OfferService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "offer")
@Api(value = "Offer´s Requests")
public class OfferController {

    private final OfferService offerService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public OfferController(OfferService offerService, JwtTokenUtil jwtTokenUtil) {
        this.offerService = offerService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping
    @ApiOperation(value = "Get all offers", notes = "return all offers")
    public List<OfferDto> findAll(
            HttpServletRequest request
    ) {
        try {
            Integer id = null;
            String requestTokenHeader = request.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
            if (requestTokenHeader != null && requestTokenHeader.startsWith(Constants.TOKEN_BEARER_PREFIX)) {
                String token = requestTokenHeader.substring(7);
                Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
                id = (Integer) claims.get("userId");
                List<String> roles = ((List<?>) claims.get("authorities"))
                        .stream()
                        .map(authority -> (String) ((Map<String, Object>) authority).get("authority"))
                        .collect(Collectors.toList());
                if (roles.contains("ROLE_" + Rol.BUSINESS.name())) {
                    return this.offerService.findAllByBusiness(id);
                }
            }
            return this.offerService.findAll(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Offers was not found.");
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Search one offer", notes = "return one offer")
    public OfferDto findById(@PathVariable Long id) {
        return this.offerService.findById(id);
    }


    @PostMapping
    @ApiOperation(value = "Create one offer", notes = "return one offer")
    @ResponseStatus(HttpStatus.CREATED)
    public OfferDto create(
            @Valid @RequestBody OfferDto offerDto
    ) {
        return this.offerService.create(offerDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Create one offer", notes = "return one offer")
    @ResponseStatus(HttpStatus.OK)
    public OfferDto update(
            @PathVariable Long id,
            @Valid @RequestBody OfferDto offerDto
    ) {
        return this.offerService.update(offerDto, id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete one offer", notes = "Offer deleted")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        this.offerService.delete(id);
    }

    @PostMapping("/change-status/{id}")
    @ApiOperation(value = "Create one offer", notes = "return one offer")
    @ResponseStatus(HttpStatus.CREATED)
    public OfferDto changeStatus(@PathVariable Long id) {
        return this.offerService.changeStatus(id);
    }

}
