package uz.islombek.course.springsecurity.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;
    //bu classda token generatsiya qilamiz va validatsiya qilamiz
    public String generateToken(String username) {
        //bu yerda xozirgi vaqtga 60 minut qo'shib
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject("User Details")
                .withClaim("username", username) //body
                .withIssuedAt(new Date()) //hozirgi vaqtda yaratamiz
                .withIssuer("Islombek") //kim yaratgani
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("Islombek")
                .build();

        DecodedJWT jwt = verifier.verify(token); //bu yerda aniqlashtirdik token biz tarafdan berilganligiga
        return jwt.getClaim("username").asString();

    }

}
