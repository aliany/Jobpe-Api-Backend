package com.ironhack.business.services;

import com.ironhack.business.dtos.WorkdayDto;
import com.ironhack.business.repositories.WorkdayRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class WorkdayService extends BaseService {

    private final WorkdayRepository workdayRepository;

    public WorkdayService(
            WorkdayRepository workdayRepository
    ) {
        this.workdayRepository = workdayRepository;
    }

    public List<WorkdayDto> findAll() {
        return this.workdayRepository
                .findAll()
                .stream()
                .map(requestedProfessionalExperienceBD -> this.toDto(requestedProfessionalExperienceBD))
                .collect(Collectors.toList());
    }

    public WorkdayDto findById(Long id) {
        return this.workdayRepository
                .findById(id)
                .stream()
                .map(requestedProfessionalExperienceBD -> this.toDto(requestedProfessionalExperienceBD))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The Contract Type was not found."));
    }
}
