package com.ironhack.users.dtos.jwt;

import com.ironhack.users.dtos.BusinessDto;
import com.ironhack.users.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JwtResponse {

    private String token;
    private String email;
    private Rol rol;
    private Long userId;
    private BusinessDto business;
}
