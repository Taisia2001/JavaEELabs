package org.shutiak.lab8.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

@Component
public class JwtTokenGenerator {

    private static final Key TOKEN_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);



    public String generateToken(final String username, Collection<? extends GrantedAuthority> authorities) {
        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities)
                .setExpiration(Date.valueOf((LocalDate.now().plusDays(3))))
                .signWith(TOKEN_KEY)
                .compact();
    }

    public String getUsernameFromToken(final String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(TOKEN_KEY)
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }

}
