package com.ironhack.business.services;

import com.ironhack.business.dtos.BusinessDto;
import com.ironhack.business.dtos.UserDto;
import com.ironhack.business.repositories.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BusinessService extends BaseService {

    private final BusinessRepository businessRepository;

    @Autowired
    public BusinessService(
            BusinessRepository businessRepository
    ) {
        this.businessRepository = businessRepository;
    }

    public List<BusinessDto> findAll() {
        try {
            List<UserDto> userDtos = this.findAllUsers();
            return this.businessRepository
                    .findAll()
                    .stream()
                    .map(businessBD -> {
                        BusinessDto result = this.toDto(businessBD);
                        result.setUserDto(
                                userDtos
                                        .stream()
                                        .filter((uDto -> uDto.getId().equals(result.getUserId())))
                                        .findFirst()
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The User for business was not found."))
                        );
                        return result;
                    })
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The business was not found.");
        }

    }

    public BusinessDto findById(Long id) {
        return this.businessRepository
                .findById(id)
                .stream()
                .map(businessBD -> {
                    try {
                        BusinessDto result = this.toDto(businessBD);
                        UserDto userDto = this.findUser(result.getUserId());
                        result.setUserDto(userDto);
                        return result;
                    } catch (Exception ex) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The User for business was not found.");
                    }
                })
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The business was not found."));
    }

    public BusinessDto create(BusinessDto businessDto) {
        try {
            return Optional.of(businessDto)
                    .map(tDto -> toEntity(tDto))
                    .map(this.businessRepository::save)
                    .map(entity -> toDto(entity))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The business was not created."));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The business was not created.");
        }
    }

    public BusinessDto update(BusinessDto businessDto, Long id) {
        try {
            BusinessDto businessDtoBD = this.findById(id);
            businessDtoBD.setName(businessDto.getName());
            return Optional.of(businessDtoBD)
                    .map(tDto -> toEntity(tDto))
                    .map(this.businessRepository::save)
                    .map(entity -> toDto(entity))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The business was not updated."));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The business was not updated.");
        }
    }

    public void delete(Long id) {
        try {
            this.businessRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The business was not deleted.");
        }
    }

    public BusinessDto findByUserId(Long id) {
        return this.businessRepository
                .findByUserId(id)
                .stream()
                .map(businessBD -> this.toDto(businessBD))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The business was not found."));
    }

}
