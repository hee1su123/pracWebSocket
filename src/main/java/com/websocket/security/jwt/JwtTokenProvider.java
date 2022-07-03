package com.websocket.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private long tokenValidMilisecond = 1000L * 60 * 60;

    public String generateToken(String name) {
        Date now = new Date();
        return Jwts.builder()
                .setId(name)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getUerNameFromJwt(String jwt) {
        return this.getClaims(jwt).getBody().getId();
    }

    public boolean validateToken(String jwt) {
        return this.getClaims(jwt) != null;
    }

    private Jws<Claims> getClaims(String jwt) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
            throw ex;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
            throw ex;
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
            throw ex;
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
            throw ex;
        }
    }

}
