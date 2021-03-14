package com.ironhack.business.dtos;

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
public class ContractTypeDto {

    private Long id;
    @NotNull
    private String type;
    private Date createdDate;
    private Date updatedDate;
}
