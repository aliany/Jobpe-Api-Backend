package com.ironhack.users.controllers;

import com.ironhack.users.dtos.ProfileDto;
import com.ironhack.users.services.ProfileService;
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
@RequestMapping(value = "profile")
@Api(value = "Profile´s Requests")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    @ApiOperation(value = "Get all Users Profile", notes = "return all Users Profile")
    public List<ProfileDto> findAll() {
        return this.profileService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Search one User Profile", notes = "return one User Profile")
    public ProfileDto findById(@PathVariable Long id) {
        return this.profileService.findById(id);
    }


    @PostMapping
    @ApiOperation(value = "Create one User Profile", notes = "return one User Profile")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileDto create(
            @Valid @RequestBody ProfileDto profileDto
    ) {
        return this.profileService.create(profileDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Create one User Profile", notes = "return one User Profile")
    @ResponseStatus(HttpStatus.OK)
    public ProfileDto update(
            @PathVariable Long id,
            @Valid @RequestBody ProfileDto profileDto
    ) {
        return this.profileService.update(profileDto, id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete one User Profile", notes = "UserProfile deleted")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        this.profileService.delete(id);
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "Search one User Profile", notes = "return one User Profile")
    public ProfileDto findByUserId(@PathVariable Long id,
                                   HttpServletRequest request
    ) {
        this.profileService.setToken(request.getHeader(Constants.AUTHORIZATION_HEADER_NAME));
        return this.profileService.findByUserId(id);
    }

}
