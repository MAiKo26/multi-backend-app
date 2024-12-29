package tn.maiko26.springboot.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.util.Date;
import io.jsonwebtoken.*;
import java.security.Key;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {
    // Generate a secure key using a sufficiently long secret
    private final Key secret = Keys.hmacShaKeyFor("YourVeryLongAndComplexSecretKeyHerePleaseMakeItLongEnough".getBytes(StandardCharsets.UTF_8));

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour expiry
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}