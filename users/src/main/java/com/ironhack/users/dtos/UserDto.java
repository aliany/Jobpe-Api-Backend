package com.ironhack.users.dtos;

import com.ironhack.users.enums.Rol;
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
public class UserDto {

    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String name;
    @NotNull
    private Rol rol;
    private Date createdDate;
    private Date updatedDate;
}
