package com.ironhack.business.dtos;

import com.ironhack.business.enums.InscriptionsStatus;
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
public class InscriptionDto {

    private Long id;
    @NotNull
    private Long offerId;
    private OfferDto offer;
    @NotNull
    private Integer userId;
    private UserDto userDto;
    @Builder.Default
    private InscriptionsStatus status = InscriptionsStatus.ENROLLED;
    private Date createdDate;
    private Date updatedDate;
}
