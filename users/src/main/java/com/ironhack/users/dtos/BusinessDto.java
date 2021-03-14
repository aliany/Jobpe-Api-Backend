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
public class BusinessDto {

    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private String name;
    private Date createdDate;
    private Date updatedDate;
}