package com.esliceu.deliveryPizza.Filter;

import com.esliceu.deliveryPizza.Model.Person;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.String.format;


@Component
public class JwtTokenUtil {
    private final static Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("jwt.secret")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int expirationTime;


    public String generateAccessToken(Person user){
        return Jwts.builder()
                .setSubject(format("%s", user.getEmail()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationTime * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public String getUsernameToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean validateToken(String token){
        System.out.printf("*****TOKEN DE UTIL ");
        System.out.printf(token);
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
            logger.error("Token mal formado");
        }catch (UnsupportedJwtException e){
            logger.error("Token no soportado");
        }catch (ExpiredJwtException e){
            logger.error("Token expirado");
        }catch (IllegalArgumentException e){
            logger.error("Token vacio");
        }catch (SignatureException e){
            logger.error("Fallo con la firma");
        }
        return false;
    }




}
