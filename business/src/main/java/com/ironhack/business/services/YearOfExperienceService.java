package com.ironhack.business.services;

import com.ironhack.business.dtos.YearsOfExperienceDto;
import com.ironhack.business.repositories.YearsOfExperienceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class YearOfExperienceService extends BaseService {

    private final YearsOfExperienceRepository yearsOfExperienceRepository;

    public YearOfExperienceService(
            YearsOfExperienceRepository yearsOfExperienceRepository
    ) {
        this.yearsOfExperienceRepository = yearsOfExperienceRepository;
    }

    public List<YearsOfExperienceDto> findAll() {
        return this.yearsOfExperienceRepository
                .findAll()
                .stream()
                .map(yearsOfExperienceBD -> this.toDto(yearsOfExperienceBD))
                .collect(Collectors.toList());
    }

    public YearsOfExperienceDto findById(Long id) {
        return this.yearsOfExperienceRepository
                .findById(id)
                .stream()
                .map(yearsOfExperienceBD -> this.toDto(yearsOfExperienceBD))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The Year Of Experience was not found."));
    }
}
