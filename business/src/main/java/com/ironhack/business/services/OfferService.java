package com.ironhack.business.services;

import com.ironhack.business.dtos.*;
import com.ironhack.business.enums.OfferStatus;
import com.ironhack.business.models.Business;
import com.ironhack.business.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OfferService extends BaseService {

    private final OfferRepository offerRepository;
    private final ContractTypeRepository contractTypeRepository;
    private final WorkdayRepository workdayRepository;
    private final RequestedProfessionalExperienceRepository requestedProfessionalExperienceRepository;
    private final YearsOfExperienceRepository yearsOfExperienceRepository;
    private final BusinessRepository businessRepository;
    private final InscriptionRepository inscriptionRepository;

    @Autowired
    public OfferService(
            OfferRepository offerRepository,
            ContractTypeRepository contractTypeRepository,
            WorkdayRepository workdayRepository,
            RequestedProfessionalExperienceRepository requestedProfessionalExperienceRepository,
            YearsOfExperienceRepository yearsOfExperienceRepository,
            BusinessRepository businessRepository,
            InscriptionRepository inscriptionRepository
    ) {
        this.offerRepository = offerRepository;
        this.contractTypeRepository = contractTypeRepository;
        this.workdayRepository = workdayRepository;
        this.requestedProfessionalExperienceRepository = requestedProfessionalExperienceRepository;
        this.yearsOfExperienceRepository = yearsOfExperienceRepository;
        this.businessRepository = businessRepository;
        this.inscriptionRepository = inscriptionRepository;
    }


    public List<OfferDto> findAll(Integer userId) {
        try {
            List<Long> inscriptionsIds = new ArrayList<>();
            if (userId != null) {
                inscriptionsIds = this.inscriptionRepository
                        .findAllByUserId(userId)
                        .stream()
                        .map(inscription -> inscription.getOffer().getId())
                        .collect(Collectors.toList());
            }
            List<Long> finalInscriptionsIds = inscriptionsIds;
            return this.offerRepository
                    .findAll()
                    .stream()
                    .map(offerBD -> {
                        OfferDto result = this.toDto(offerBD);
                        if (!finalInscriptionsIds.isEmpty() && finalInscriptionsIds.contains(result.getId())) {
                            result.setSubscribed(true);
                        }
                        return result;
                    })
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Offer was not created.");
        }
    }

    public List<OfferDto> findAllByBusiness(Integer userId) {
        try {
            Optional<Business> business = Optional.ofNullable(this.businessRepository
                    .findByUserId(Long.valueOf(userId))
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The business was not found.")));
            return this.offerRepository
                    .findAllByBusiness(business.get())
                    .stream()
                    .map(offerBD -> this.toDto(offerBD))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Offer was not created.");
        }
    }

    public OfferDto findById(Long id) {
        return this.offerRepository
                .findById(id)
                .stream()
                .map(offerBD -> this.toDto(offerBD))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The Offer was not found."));
    }

    public OfferDto create(OfferDto offerDto) {
        try {
            this.prepareToSave(offerDto, true);
            return Optional.of(offerDto)
                    .map(tDto -> toEntity(tDto))
                    .map(this.offerRepository::save)
                    .map(entity -> toDto(entity))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Offer was not created."));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Offer was not created.");
        }
    }

    public OfferDto update(OfferDto offerDto, Long id) {
        try {
            OfferDto OfferFromDB = this.findById(id);
            OfferFromDB.setTitle(offerDto.getTitle());
            OfferFromDB.setDescription(offerDto.getDescription());
            OfferFromDB.setRequirements(offerDto.getRequirements());
            if (!offerDto.getSalaryAmountMin().equals(null)) {
                OfferFromDB.setSalaryAmountMin(offerDto.getSalaryAmountMin());
            }
            if (!offerDto.getSalaryAmountMax().equals(null)) {
                OfferFromDB.setSalaryAmountMin(offerDto.getSalaryAmountMax());
            }
            if (!offerDto.getLocation().equals(null)) {
                OfferFromDB.setLocation(offerDto.getLocation());
            }
            OfferFromDB.setContractTypeId(offerDto.getContractTypeId());
            OfferFromDB.setWorkdayId(offerDto.getWorkdayId());
            OfferFromDB.setRequestedProfessionalExperienceId(offerDto.getRequestedProfessionalExperienceId());
            OfferFromDB.setYearsOfExperienceId(offerDto.getYearsOfExperienceId());
            this.prepareToSave(OfferFromDB, false);
            return Optional.of(OfferFromDB)
                    .map(tDto -> toEntity(tDto))
                    .map(this.offerRepository::save)
                    .map(entity -> toDto(entity))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Offer was not updated."));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Offer was not updated.");
        }
    }

    public void delete(Long id) {
        try {
            this.offerRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Offer was not deleted.");
        }
    }

    public OfferDto changeStatus(Long id) {
        try {
            return Optional.of(this.findById(id))
                    .map(tDto -> {
                        if (tDto.getStatus().equals(OfferStatus.CLOSED)) {
                            tDto.setStatus(OfferStatus.OPENED);
                        } else {
                            tDto.setStatus(OfferStatus.CLOSED);
                        }
                        return toEntity(tDto);
                    })
                    .map(this.offerRepository::save)
                    .map(entity -> toDto(entity))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Offer was not closed."));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Offer was not closed.");
        }
    }

    private void prepareToSave(OfferDto offerDto, boolean isNew) {
        ContractTypeDto contractTypeDto = this.findContractTypeById(offerDto.getContractTypeId());
        WorkdayDto workdayDto = this.findWorkdayById(offerDto.getWorkdayId());
        RequestedProfessionalExperienceDto requestedProfessionalExperienceDto = this.findRequestedProfessionalExperienceById(offerDto.getRequestedProfessionalExperienceId());
        YearsOfExperienceDto yearsOfExperienceDto = this.findYearsOfExperienceById(offerDto.getYearsOfExperienceId());
        offerDto.setContractType(contractTypeDto);
        offerDto.setWorkday(workdayDto);
        offerDto.setRequestedProfessionalExperience(requestedProfessionalExperienceDto);
        offerDto.setYearsOfExperience(yearsOfExperienceDto);
        if (isNew) {
            BusinessDto businessDto = this.findBusinessById(offerDto.getBusinessId());
            offerDto.setBusiness(businessDto);
        }
    }

    private ContractTypeDto findContractTypeById(Long id) {
        return this.contractTypeRepository
                .findById(id)
                .stream()
                .map(contractTypeDB -> toDto(contractTypeDB))
                .findFirst()
                .orElse(null);
    }

    private WorkdayDto findWorkdayById(Long id) {
        return this.workdayRepository
                .findById(id)
                .stream()
                .map(workdayDB -> toDto(workdayDB))
                .findFirst()
                .orElse(null);
    }

    private RequestedProfessionalExperienceDto findRequestedProfessionalExperienceById(Long id) {
        return this.requestedProfessionalExperienceRepository
                .findById(id)
                .stream()
                .map(requestedProfessionalExperienceDB -> toDto(requestedProfessionalExperienceDB))
                .findFirst()
                .orElse(null);
    }

    private YearsOfExperienceDto findYearsOfExperienceById(Long id) {
        return this.yearsOfExperienceRepository
                .findById(id)
                .stream()
                .map(yearsOfExperienceDB -> toDto(yearsOfExperienceDB))
                .findFirst()
                .orElse(null);
    }

    private BusinessDto findBusinessById(Long id) {
        return this.businessRepository
                .findById(id)
                .stream()
                .map(businessDB -> toDto(businessDB))
                .findFirst()
                .orElse(null);
    }
}
