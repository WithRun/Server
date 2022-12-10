package com.example.WithRun.security;

import com.example.WithRun.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {
    private static final String SECRET_KEY = "WITHRUN";

    public String create(User user) {
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(Long.toString(user.getId())).setIssuer("WithRun")
                .setIssuedAt(new Date()).setExpiration(expiryDate)
                .compact();
    }

    public String validateAndGetUserId(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }



}
