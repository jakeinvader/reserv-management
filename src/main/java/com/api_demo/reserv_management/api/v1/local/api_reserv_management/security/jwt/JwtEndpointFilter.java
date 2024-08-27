package com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.userDetails.MyUserDetailsService;
import com.api_demo.reserv_management.api.v1.local.utils.UtilsLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;


public class JwtEndpointFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    MyUserDetailsService myUserDetailsService;


    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain)
    throws ServletException, IOException {
        String uri = request.getRequestURI();
        UtilsLocal.log("URI: " + uri);
        try {
            String bearerToken = request.getHeader("Authorization");

            if (bearerToken != null && bearerToken.startsWith("Bearer "))
            {
                String token = bearerToken.replace("Bearer ", "");

                if (jwtProvider == null) jwtProvider = new JwtProvider();

                if (token != null && jwtProvider.validateToken(token, secret)) {

                    String userEmail = jwtProvider.getUserEmailFromToken(token, secret);

                   //List<String> roles = jwtProvider.getRolesFromToken(token, secret);

                    /*List<GrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                     */

                    UserDetails userDetails = myUserDetailsService.loadUserByUsername(userEmail);
                    UtilsLocal.log("* * * Lista de Roles: " + userDetails.getAuthorities());

                    //Obtiene Autenticacion
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);;
                }
            }
            else  {
                UtilsLocal.log("................ No viene Token o es incorrecto");
            }
        }
        catch (Exception e) {
            UtilsLocal.log("................ Fallo doFilterInternal por Token");
        }

        chain.doFilter(request, response);
    }
}
