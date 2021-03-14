package com.ironhack.users.controllers;

import com.ironhack.users.dtos.UserDto;
import com.ironhack.users.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "user")
@Api(value = "User´s Requests")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation(value = "Get all Users", notes = "return all Users")
    public List<UserDto> findAll() {
        return this.userService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Search one User", notes = "return one User")
    public UserDto findById(@PathVariable Long id) {
        return this.userService.findById(id);
    }


    @PostMapping
    @ApiOperation(value = "Create one User", notes = "return one User")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(
            @Valid @RequestBody UserDto userDto
    ) {
        return this.userService.create(userDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update one User", notes = "return one User")
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(
            @PathVariable Long id,
            @Valid @RequestBody UserDto userDto
    ) {
        return this.userService.update(userDto, id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete one User", notes = "User deleted")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        this.userService.delete(id);
    }


}
