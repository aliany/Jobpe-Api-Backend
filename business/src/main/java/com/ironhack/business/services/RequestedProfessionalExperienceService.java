package com.ironhack.business.services;

import com.ironhack.business.dtos.RequestedProfessionalExperienceDto;
import com.ironhack.business.repositories.RequestedProfessionalExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RequestedProfessionalExperienceService extends BaseService {

    private final RequestedProfessionalExperienceRepository requestedProfessionalExperienceRepository;

    @Autowired
    public RequestedProfessionalExperienceService(
            RequestedProfessionalExperienceRepository requestedProfessionalExperienceRepository
    ) {
        this.requestedProfessionalExperienceRepository = requestedProfessionalExperienceRepository;
    }

    public List<RequestedProfessionalExperienceDto> findAll() {
        return this.requestedProfessionalExperienceRepository
                .findAll()
                .stream()
                .map(requestedProfessionalExperienceBD -> this.toDto(requestedProfessionalExperienceBD))
                .collect(Collectors.toList());
    }

    public RequestedProfessionalExperienceDto findById(Long id) {
        return this.requestedProfessionalExperienceRepository
                .findById(id)
                .stream()
                .map(requestedProfessionalExperienceBD -> this.toDto(requestedProfessionalExperienceBD))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The Contract Type was not found."));
    }
}
