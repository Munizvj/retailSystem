package com.security_service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.security_service.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class SecurityService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user, UserDetailsImpl userDetails){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            List<String> authorities = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            return JWT.create()
                    .withIssuer("security-service")
                    .withSubject(user.getLogin())
                    .withClaim("authorities", authorities)
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        }catch (JWTCreationException exception) {
            throw new RuntimeException("Error creating JWT token", exception);
        }
    }

    public String validateToken(String tokenJWT){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("security-service")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        }catch (JWTVerificationException exception){
            return null;
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("-03:00"));
    }

}
