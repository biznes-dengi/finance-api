package com.finance.app.auth.process;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtProcess {

    @Value("#{T(com.finance.app.process.JwtProcess).getSecretKey('${spring.security.jwt.secret-key:undefined}')}")
    private Key secretKey;
    @Value("${spring.security.jwt.expiration-time:24}")
    private Long expirationTime;

    public String generateToken(final String username) {
        final var claims = new HashMap<String, Object>();
        return createToken(claims, username);
    }

    private String createToken(final Map<String, Object> claims, final String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(expirationTime)))
                .signWith(this.secretKey)
                .compact();
    }

    public Boolean validateToken(final String token) {
        return Boolean.FALSE.equals(isTokenExpired(token));
    }

    private Boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static Key getSecretKey(final String value) {
        return "undefined".equals(value) ? Keys.secretKeyFor(SignatureAlgorithm.HS512) : Keys.hmacShaKeyFor(value.getBytes());
    }
}
