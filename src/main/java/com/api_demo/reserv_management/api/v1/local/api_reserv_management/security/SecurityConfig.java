package com.api_demo.reserv_management.api.v1.local.api_reserv_management.security;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.jwt.JwtAuthenticationFilter;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.jwt.JwtEndpointFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Integer expiration;

    @Autowired
    private UserDetailsService userDetailsService;

    // Enlaza la Auth dle servicio (AuthService) para poderhacer el login y generar el token
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    //Es el filtro por Token, de cada peticion protegida
    @Bean
    public JwtEndpointFilter jwtEndpointFilter() { return new JwtEndpointFilter(); }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager)
            throws Exception {

        jwtAuthenticationFilter = new JwtAuthenticationFilter( authManager, secret, expiration );

        http
            .cors()
            .and().csrf().disable() //Inhabilitar los cookies
            .authorizeRequests()
                .requestMatchers("/local/api-reserv-management/auth/**").permitAll()//para lo que tenga "auth" permitir sin token!
                .requestMatchers("/doc/**").permitAll()
                .requestMatchers("/api-docs/**").permitAll()
                //.requestMatchers("/local/api-reserv-management/users/**").hasRole("ADMIN")
                .anyRequest().authenticated() //Para todo lo demas, debe estar autenticado (protegido)
            .and()
            .sessionManagement() //Politica de sesion sin estado
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(jwtAuthenticationFilter);

        http.addFilterBefore(jwtEndpointFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception
    {
        return http.getSharedObject( AuthenticationManagerBuilder.class )
                .userDetailsService( userDetailsService )
                .passwordEncoder( passwordEncoder() )
                .and()
                .build();
    }
}
