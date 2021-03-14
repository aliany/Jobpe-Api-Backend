package com.ironhack.users.controllers;

import com.ironhack.users.configurations.security.Constants;
import com.ironhack.users.configurations.security.jwt.JwtTokenUtil;
import com.ironhack.users.dtos.BusinessDto;
import com.ironhack.users.dtos.jwt.JwtRequest;
import com.ironhack.users.dtos.jwt.JwtResponse;
import com.ironhack.users.enums.Rol;
import com.ironhack.users.models.CustomUserDetail;
import com.ironhack.users.services.BaseService;
import com.ironhack.users.services.JwtUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    private final BaseService baseService;

    public LoginController(
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            JwtUserDetailsService jwtUserDetailsService,
            BaseService baseService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.baseService = baseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
            throws Exception {
        BusinessDto businessDto = null;
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final CustomUserDetail userDetails = jwtUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        if (userDetails.getRol().equals(Rol.BUSINESS)) {
            baseService.setToken(Constants.TOKEN_BEARER_PREFIX + token);
            businessDto = baseService.findByBusinessUserId(userDetails.getId());
        }
        return ResponseEntity.ok(
                JwtResponse
                        .builder()
                        .token(token)
                        .email(userDetails.getUsername())
                        .userId(userDetails.getId())
                        .rol(userDetails.getRol())
                        .business(businessDto)
                        .build()
        );
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
