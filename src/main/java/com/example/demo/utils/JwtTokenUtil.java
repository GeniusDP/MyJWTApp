package com.example.demo.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecret;

    @Value("${zaranik.app.jwtAccessTokenExpirationMs}")
    private int jwtAccessTokenExpirationMs;

    @Value("${zaranik.app.jwtRefreshTokenExpirationMs}")
    private int jwtRefreshTokenExpirationMs;

    public String generateAccessJwtToken(String username) {
        return generateJwtToken(username, jwtAccessTokenExpirationMs);
    }

    public String generateRefreshJwtToken(String username) {
        return generateJwtToken(username, jwtRefreshTokenExpirationMs);
    }


    private String generateJwtToken(String username, int expirationMillis) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expirationMillis))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }


    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean tokenIsValid(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
