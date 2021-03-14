package com.ironhack.users.services;

import com.ironhack.users.dtos.BusinessDto;
import com.ironhack.users.dtos.UserDto;
import com.ironhack.users.enums.Rol;
import com.ironhack.users.models.User;
import com.ironhack.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDto> findAll() {
        return this.userRepository
                .findAll()
                .stream()
                .map(userBD -> this.toDto(userBD))
                .collect(Collectors.toList());
    }

    public UserDto findById(Long id) {
        return this.userRepository
                .findById(id)
                .stream()
                .map(userBD -> this.toDto(userBD))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The User was not found."));
    }

    @Transactional(rollbackOn = Exception.class)
    public UserDto create(UserDto userDto) {
        try {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            return Optional.of(userDto)
                    .map(tDto -> toEntity(tDto))
                    .map(this.userRepository::save)
                    .map(entity -> {
                        if (entity.getRol().equals(Rol.BUSINESS)) {
                            this.createBusiness(
                                    BusinessDto.builder()
                                            .userId(entity.getId())
                                            .name(userDto.getName())
                                            .build()
                            );
                        }
                        return toDto(entity);
                    })
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The User was not created."));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The User was not created.");
        }
    }

    private BusinessDto businessFallback() {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Business Service is down");
    }


    public UserDto update(UserDto userDto, Long id) {
        try {
            UserDto userDtoBD = this.findById(id);
//            userDtoBD.setName(userDto.getName());
            return Optional.of(userDtoBD)
                    .map(tDto -> toEntity(tDto))
                    .map(this.userRepository::save)
                    .map(entity -> toDto(entity))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The User was not updated."));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The User was not updated.");
        }
    }

    public void delete(Long id) {
        try {
            this.userRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The User was not deleted.");
        }
    }


}
