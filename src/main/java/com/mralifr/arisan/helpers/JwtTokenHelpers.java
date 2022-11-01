package com.mralifr.arisan.helpers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

import static io.jsonwebtoken.io.Decoders.BASE64;

@Component
@Getter
public class JwtTokenHelpers {
    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expired.time.minute}")
    private Long expiredTimeInMinute;

    @Value("${jwt.secret.key}")
    private String secretKey;

    public String generateToken(String principal, Set<String> roles) {
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiredAt = issuedAt.plusMinutes(expiredTimeInMinute);
        return Jwts.builder()
                .setIssuer(issuer)
                .setAudience(principal)
                .setIssuedAt(Date.from(issuedAt.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
                .claim("roles", roles)
                .signWith(Keys.hmacShaKeyFor(BASE64.decode(secretKey)))
                .compact();
    }

    public Jws<Claims> parseToken(String jwsString) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(BASE64.decode(secretKey)))
                .build()
                .parseClaimsJws(jwsString);
    }


}