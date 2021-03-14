package com.ironhack.business.configurations.security.jwt;

import com.ironhack.business.configurations.security.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String username = null;
        Claims claims = null;
        String requestTokenHeader = request.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
        if (requestTokenHeader == null || !requestTokenHeader.startsWith(Constants.TOKEN_BEARER_PREFIX)) {
            chain.doFilter(request, response);        // If not valid, go to the next filter.
            return;
        }
        String token = requestTokenHeader.substring(7);
        try {
            try {
                username = jwtTokenUtil.getUsernameFromToken(token);
                claims = jwtTokenUtil.getAllClaimsFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
            if (username != null) {
                @SuppressWarnings("unchecked")
                List<String> authorities = (List<String>) claims.get("authorities");
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        ((List<?>) claims.get("authorities"))
                                .stream()
                                .map(authority -> new SimpleGrantedAuthority(
                                        (String) ((Map<String, Object>) authority).get("authority"))
                                )
                                .collect(Collectors.toList())
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }

}
