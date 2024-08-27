package com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.jwt;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.payload.LoginDto;
import com.api_demo.reserv_management.api.v1.local.utils.UtilsLocal;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private String secret;
    private Integer expiration;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(
            AuthenticationManager authManager,
            String secret,
            Integer expiration
    ) {
        this.authenticationManager = authManager;
        this.expiration = expiration;
        this.secret = secret;
    }

    // Logica de autenticacion por JWT

    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {

        LoginDto auth = new LoginDto();
        try {
            auth = new ObjectMapper().readValue(request.getReader(), LoginDto.class);
        }
        catch (Exception e) {
            UtilsLocal.log("Ha fallado el metodo: attemptAuthentication()");
            UtilsLocal.log(e.getMessage());
        }

        UsernamePasswordAuthenticationToken userPAT = new UsernamePasswordAuthenticationToken(
            auth.getEmail(),
            auth.getPassword(),
            Collections.emptyList()
        );

        String message = "Usuario o contraseña incorrecto";
        try {
            Authentication authResult = authenticationManager.authenticate(userPAT);
            return authResult;
        }
        catch (BadCredentialsException ex) {
            UtilsLocal.log("............. Error - BadCredentialsException:");
            UtilsLocal.log(ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                response.getWriter().write(message);
            } catch (IOException ioException) {
                UtilsLocal.log(ioException.getMessage());
            }
            throw ex;
        }
        catch (Exception exb) {
            UtilsLocal.log(exb.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                response.getWriter().write(message);
            } catch (IOException ioException) {
                UtilsLocal.log(ioException.getMessage());
            }
            throw exb;
        }
    }
}
