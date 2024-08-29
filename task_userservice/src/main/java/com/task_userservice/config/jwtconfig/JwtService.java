package com.task_userservice.config.jwtconfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final String SECRET = "638CBE3A90E0303BF3808F40F95A7F02A24B4B5D029C954CF553F79E9EF1DC0384BE681C249F1223F6B55AA21DC070914834CA22C8DD98E14A872CA010091ACC";
    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);

    public String generateToken(Authentication authentication) {
        Map<String, String> claims = new HashMap<>();
        claims.put("iss", "pabitra");
        return Jwts.builder()
                .claims(claims)
                .subject(authentication.getName())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .claim("scope", createScope(authentication))
                .compact();
    }

    private SecretKey generateKey() {

        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUserName(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public boolean isTokenValid(String jwt) {
        try {
            Claims claims = getClaims(jwt);
            return claims.getExpiration().after(Date.from(Instant.now()));
        } catch (JwtException | IllegalArgumentException e) {
            // Log token validation error
            System.err.println("Invalid JWT token: " + e.getMessage());
            return false;
        }

    }
    private String createScope(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(" "));
    }
}