package com.cadastroAdv.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cadastroAdv.securityAll.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret; //Secret da aplicação para unir ao HASH

    public String gerarToken(User user){
        try{
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(TempoDeExpiracao())
                    .sign(algoritmo);
            return token;

        } catch (JWTCreationException ex){
            throw new RuntimeException("Error na geração do Token." ,ex);

        }
    }




    public String validaToken(String token){
        try{
            Algorithm algorithmo = Algorithm.HMAC256(secret);
            return JWT.require(algorithmo)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            return "";
        }
    }


    private Instant TempoDeExpiracao(){ //TEMPO DE EXPIRAÇÃO DO TOKEN
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
