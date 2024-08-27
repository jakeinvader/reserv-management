package com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.jwt;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.userDetails.UserDetailsImpl;
import com.api_demo.reserv_management.api.v1.local.utils.UtilsLocal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    public String generateToken(Authentication auth, String secret, Integer expiration) {
        //Obtiene el usuario
        UserDetailsImpl authUserRols = (UserDetailsImpl) auth.getPrincipal();
        //Crea el token
        return Jwts.builder()
                .setSubject(authUserRols.getUsername())
                .setIssuer(authUserRols.getName())
                //.claim("role", authUserRols.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUserEmailFromToken(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
                .getBody().getSubject();
    }

   /* public List<String> getRolesFromToken(String token, String secret) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return (List<String>) claims.get("role");
    }
    */

    public String getUserFromToken(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
                .getBody().getIssuer();
    }

    public boolean validateToken(String token, String secret) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }
        catch (MalformedJwtException e) {
            UtilsLocal.log("Token mal construido");
        }
        catch (UnsupportedJwtException e) {
            UtilsLocal.log("Token no soportado");
        }
        catch (ExpiredJwtException e) {
            UtilsLocal.log("Token expirado/caducado.");
        }
        catch (IllegalArgumentException e) {
            UtilsLocal.log("Token vacio.");
        }
        catch (SignatureException e) {
            UtilsLocal.log("Error en la firma del Yoken.");
        }
        return false;
    }
}
