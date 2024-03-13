package com.domain.library.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY="GqELXcPO2T04X8xNiVtIgiNtQW4nU2zKGqELXcPO2T04X8xNiVtIgiNtQW4nU2zK";

    public String generateToken(String email){
        return generateToken(new HashMap<>(), email);
    }

    private String generateToken(HashMap<String, Objects> extraClaims, String email) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .subject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //////////////////////////////////////// Token Decode ////////////////////////////////////////////////////
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build().parseClaimsJws(token)
                .getBody();
    }
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }
    public String extractEmail(String token){
        return extractClaims(token, Claims::getSubject);
    }
    //////////////////////////////////// Is it valid ///////////////////////////////////////////////////////////

    public boolean isValid(String token, UserDetails userDetails){
        String email = extractEmail(token);
        return (userDetails.getUsername().equals(email)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

}













