package com.ironhack.business.services;

import com.ironhack.business.dtos.InscriptionDto;
import com.ironhack.business.dtos.OfferDto;
import com.ironhack.business.dtos.UserDto;
import com.ironhack.business.enums.InscriptionsStatus;
import com.ironhack.business.models.Inscription;
import com.ironhack.business.repositories.InscriptionRepository;
import com.ironhack.business.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class InscriptionService extends BaseService {

    private final InscriptionRepository inscriptionRepository;
    private final OfferRepository offerRepository;

    @Autowired
    public InscriptionService(
            InscriptionRepository inscriptionRepository,
            OfferRepository offerRepository
    ) {
        this.inscriptionRepository = inscriptionRepository;
        this.offerRepository = offerRepository;
    }

    public InscriptionDto findById(Long id) {
        List<UserDto> userDtos = this.findAllUsers();
        return this.inscriptionRepository
                .findById(id)
                .stream()
                .map(inscriptionBD -> {
                    InscriptionDto result = this.toDto(inscriptionBD);
                    result.setUserDto(
                            userDtos
                                    .stream()
                                    .filter(uDto -> uDto.getId().equals(Long.valueOf(result.getUserId())))
                                    .findFirst()
                                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The User for Inscription was not found."))
                    );
                    return result;
                })
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The Inscription was not found."));
    }


    public InscriptionDto save(InscriptionDto inscriptionDto, Long id, InscriptionsStatus inscriptionsStatus) {
        try {
            InscriptionDto inscriptionDtoToSave = inscriptionDto;
            if (id != null) {
                inscriptionDtoToSave = this.findById(id);
                inscriptionDtoToSave.setStatus(inscriptionsStatus);
            }
            if (inscriptionDto != null) {
                OfferDto offerDto = this.offerRepository
                        .findById(inscriptionDto.getOfferId())
                        .stream()
                        .map(offerBD -> this.toDto(offerBD))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The Offer was not found."));
                inscriptionDtoToSave.setOffer(offerDto);
            }
            return Optional.of(inscriptionDtoToSave)
                    .map(tDto -> toEntity(tDto))
                    .map(this.inscriptionRepository::save)
                    .map(entity -> toDto(entity))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Inscription was not created."));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Inscription was not created.");
        }
    }

    public void delete(Long id) {
        try {
            this.inscriptionRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Inscription was not deleted.");
        }
    }

    public void unenrolled(InscriptionDto inscriptionDto) {
        try {
            Inscription inscription = this.inscriptionRepository.findByOfferIdAndUserId(
                    inscriptionDto.getOfferId(),
                    inscriptionDto.getUserId()
            );
            if (Objects.isNull(inscription)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Inscription was not founded.");
            }
            this.inscriptionRepository.deleteById(inscription.getId());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The Inscription was not deleted.");
        }
    }
}
