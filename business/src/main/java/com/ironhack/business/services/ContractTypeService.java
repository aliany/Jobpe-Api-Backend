package com.ironhack.business.services;

import com.ironhack.business.dtos.ContractTypeDto;
import com.ironhack.business.repositories.ContractTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractTypeService extends BaseService {

    private final ContractTypeRepository contractTypeRepository;

    @Autowired
    public ContractTypeService(
            ContractTypeRepository contractTypeRepository
    ) {
        this.contractTypeRepository = contractTypeRepository;
    }

    public List<ContractTypeDto> findAll() {
        return this.contractTypeRepository
                .findAll()
                .stream()
                .map(contractTypeBD -> this.toDto(contractTypeBD))
                .collect(Collectors.toList());
    }

    public ContractTypeDto findById(Long id) {
        return this.contractTypeRepository
                .findById(id)
                .stream()
                .map(contractTypeBD -> this.toDto(contractTypeBD))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The Contract Type was not found."));
    }
}
