package com.example.demo.auth;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

@Service
public class JwtTokenAutenticate {
	
   @Value("${api.security.token.secret}")
   private String secret;

    private static final String ISSUER = "tce-pe";

    
    public String generateToken(String email) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plus(1, ChronoUnit.DAYS)) 
                    .withSubject(email)
                    .withClaim("login", email)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }
    
    public String getSubject(String token) {
    	return JWT.require(Algorithm.HMAC256(secret))
    			.withIssuer(ISSUER)
    			.build().verify(token).getSubject();
    	
    }
    
    public LocalDateTime getUltimoLogin(String token) {
    	var date = JWT.require(Algorithm.HMAC256(secret))
    			.withIssuer(ISSUER)
    			.build().verify(token).getIssuedAt();
    	return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

}
