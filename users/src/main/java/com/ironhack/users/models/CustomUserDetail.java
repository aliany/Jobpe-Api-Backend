package com.ironhack.users.models;

import com.ironhack.users.enums.Rol;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class CustomUserDetail extends org.springframework.security.core.userdetails.User {

    private Long id;
    private Rol rol;

    public CustomUserDetail(
            Long id,
            Rol rol,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(username, password, authorities);
        this.id = id;
        this.rol = rol;
    }

    public CustomUserDetail(
            String username,
            String password,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
