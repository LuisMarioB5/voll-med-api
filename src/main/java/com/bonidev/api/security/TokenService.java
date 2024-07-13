package com.bonidev.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bonidev.api.model.entity.UsuarioEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    private final String JWT_SECRET = System.getenv("JWT_SECRET");

    public String generarToken(UsuarioEntity usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("123456");
            return JWT.create()
                    .withIssuer("Voll Med")
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("El token no puede ser nulo.");
        }

        DecodedJWT decodedJWT = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            decodedJWT = JWT.require(algorithm)
                    .withIssuer("Voll Med")
                    .build()
                    .verify(token);

        } catch (JWTVerificationException exception) {
            throw new RuntimeException(exception.getMessage());
        }

        assert decodedJWT != null;
        return decodedJWT.getSubject();
    }
}
