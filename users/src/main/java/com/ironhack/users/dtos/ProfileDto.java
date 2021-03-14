package com.ironhack.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileDto {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    private String photo;
    private Date birthdate;
    private String address;
    private String postalCode;
    @NotNull
    private String phoneNumber;
    private String presentationText;
    @NotNull
    private Long userId;
    private UserDto user;
    @NotNull
    private Long requestedProfessionalExperienceId;
    private RequestedProfessionalExperienceDto requestedProfessionalExperience;
    @NotNull
    private Long yearsOfExperienceId;
    private YearsOfExperienceDto yearsOfExperience;
    private Date createdDate;
    private Date updatedDate;

}
