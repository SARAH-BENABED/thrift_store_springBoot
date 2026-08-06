package com.example.demo;

import io.jsonwebtoken.security.Keys ;
import java.security.Key ;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts ;
import io.jsonwebtoken.SignatureAlgorithm ;
import java.util.Date ;

import org.springframework.stereotype.Service ;
import org.springframework.beans.factory.annotation.Value ;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET  ;

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes()) ;
    }
    public String generateToken(String email, String role) {
        return Jwts.builder().setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 1000*10*60))
                .signWith(getKey())
                .compact() ;
    }

    public String extractEmail(String token) {
        return Jwts.parser().setSigningKey(getKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject() ;
    }

    public String extractRole(String token) {
        return Jwts.parser().setSigningKey(getKey())
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class) ;
    }
}

