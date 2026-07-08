package com.microsoft.quantum.qlmsauthservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key =
            Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRATION = 1000 * 60 * 60 * 24;

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(
                        System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }
}
