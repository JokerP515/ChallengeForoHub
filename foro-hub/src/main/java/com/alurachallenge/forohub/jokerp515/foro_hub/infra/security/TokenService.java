package com.alurachallenge.forohub.jokerp515.foro_hub.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.alurachallenge.forohub.jokerp515.foro_hub.domain.usuarios.Usuario;

@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String apiSecret;
    @Value("${api.security.issuer}")
    private String apiIssuer;
    @Value("${api.security.expiration}")
    private Long apiExpiration;

    public String generarToken(Usuario usuario){
        try{
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer(apiIssuer)
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        }catch(JWTCreationException e){
            throw new RuntimeException("Error al crear el token");
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("Token inválido");
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); // validando firma
            verifier = JWT.require(algorithm)
                    .withIssuer(apiIssuer)
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
        }
        if (verifier == null || verifier.getSubject() == null) {
            throw new RuntimeException("Verifier inválido");
        }
        return verifier.getSubject();
    }

    // Genera una fecha de expiracion para el token en base al horario actual UTC-5
    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(apiExpiration).toInstant(ZoneOffset.of("-05:00"));
    }
}
