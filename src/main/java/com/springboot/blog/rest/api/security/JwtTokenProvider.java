package com.springboot.blog.rest.api.security;

import com.springboot.blog.rest.api.exception.BlogApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationDate;

    //generate token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date deadLine =  new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(deadLine)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return  token;
    }

    //get username from token
    public String getUserNameFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    //validate jwt token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        }catch (MalformedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        }catch (ExpiredJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        }catch (UnsupportedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }
    }

}
