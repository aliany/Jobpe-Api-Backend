package com.ironhack.business.dtos;

import com.ironhack.business.enums.OfferStatus;
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
public class OfferDto {

    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String requirements;
    private String salaryAmountMinFormatted;
    private Double salaryAmountMin;
    private String salaryAmountMaxFormatted;
    private Double salaryAmountMax;
    private String location;
    @Builder.Default
    private OfferStatus status = OfferStatus.OPENED;
    @NotNull
    private Long contractTypeId;
    private ContractTypeDto contractType;
    @NotNull
    private Long workdayId;
    private WorkdayDto workday;
    @NotNull
    private Long requestedProfessionalExperienceId;
    private RequestedProfessionalExperienceDto requestedProfessionalExperience;
    @NotNull
    private Long yearsOfExperienceId;
    private YearsOfExperienceDto yearsOfExperience;
    @NotNull
    private Long businessId;
    private BusinessDto business;
    @Builder.Default
    private Boolean Subscribed = false;
    private Date createdDate;
    private Date updatedDate;
}
