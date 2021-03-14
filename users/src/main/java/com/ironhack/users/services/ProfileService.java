package com.ironhack.users.services;

import com.ironhack.users.dtos.ProfileDto;
import com.ironhack.users.dtos.RequestedProfessionalExperienceDto;
import com.ironhack.users.dtos.YearsOfExperienceDto;
import com.ironhack.users.models.Profile;
import com.ironhack.users.models.User;
import com.ironhack.users.repositories.ProfileRepository;
import com.ironhack.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService extends BaseService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProfileService(
            ProfileRepository profileRepository,
            UserRepository userRepository
    ) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public List<ProfileDto> findAll() {
        return this.profileRepository
                .findAll()
                .stream()
                .map(profileBD -> this.toDto(profileBD))
                .collect(Collectors.toList());
    }

    public ProfileDto findById(Long id) {
        return this.profileRepository
                .findById(id)
                .stream()
                .map(profileBD -> this.toDto(profileBD))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The profile was not found."));
    }

    public ProfileDto create(ProfileDto profileDto) {
        try {
            return Optional.of(profileDto)
                    .map(tDto -> {
                        Profile result = toEntity(tDto);
                        User user = this.userRepository
                                .findById(profileDto.getUserId())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The user was not founded."));
                        result.setUser(user);
                        return result;
                    })
                    .map(this.profileRepository::save)
                    .map(entity -> toDto(entity))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The profile was not created."));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The profile was not created.");
        }
    }

    public ProfileDto update(ProfileDto profileDto, Long id) {
        try {
            ProfileDto profileDtoBD = this.findById(id);
            profileDtoBD.setName(profileDto.getName());
            profileDtoBD.setLastName(profileDto.getLastName());
            profileDtoBD.setBirthdate(profileDto.getBirthdate());
            profileDtoBD.setAddress(profileDto.getAddress());
            profileDtoBD.setPostalCode(profileDto.getPostalCode());
            profileDtoBD.setPhoneNumber(profileDto.getPhoneNumber());
            profileDtoBD.setPresentationText(profileDto.getPresentationText());
            profileDtoBD.setRequestedProfessionalExperienceId(profileDto.getRequestedProfessionalExperienceId());
            profileDtoBD.setYearsOfExperienceId(profileDto.getYearsOfExperienceId());
            return Optional.of(profileDtoBD)
                    .map(tDto -> toEntity(tDto))
                    .map(this.profileRepository::save)
                    .map(entity -> toDto(entity))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The profile was not updated."));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The profile was not updated.");
        }
    }

    public void delete(Long id) {
        try {
            this.profileRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The profile was not deleted.");
        }
    }

    public ProfileDto findByUserId(Long id) {
        return this.profileRepository
                .findByUserId(id)
                .stream()
                .map(profileBD -> {
                    ProfileDto profileDto = this.toDto(profileBD);
                    YearsOfExperienceDto yearsOfExperienceDto = this.findYearsOfExperienceById(profileDto.getYearsOfExperienceId());
                    RequestedProfessionalExperienceDto requestedProfessionalExperienceDto = this.findRequestedProfessionalExperienceById(profileDto.getRequestedProfessionalExperienceId());
                    profileDto.setYearsOfExperience(yearsOfExperienceDto);
                    profileDto.setRequestedProfessionalExperience(requestedProfessionalExperienceDto);
                    return profileDto;
                })
                .findFirst()
                .orElse(null);
    }
}
