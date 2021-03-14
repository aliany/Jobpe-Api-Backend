package com.ironhack.edge.dtos;

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
    private UserDto userDto;
    @NotNull
    private String name;
    private Date createdDate;
    private Date updatedDate;
}
