package com.ironhack.business.feign;

import com.ironhack.business.dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;


@FeignClient("users-ms")
public interface UserClient {

    @GetMapping("/user")
    List<UserDto> findAllUsers(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

    @GetMapping("/user/{id}")
    UserDto findUser(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @PathVariable Long id);

}
