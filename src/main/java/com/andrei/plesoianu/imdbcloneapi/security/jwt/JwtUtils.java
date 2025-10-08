package com.andrei.plesoianu.imdbcloneapi.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    public static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.app.jwt.expiration}")
    private int jwtExpirationMs;

    @Value("${spring.app.jwt.key}")
    private String jwtSecret;

    /**
     * Extracts the JWT token from Authorization header
     */
    public String getJwtTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    /**
     * Extract email from Jwt Token
     */
    public String extractEmailFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    /**
     * Generates signing key
     */
    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret));
    }

    /**
     * Validates Jwt Token
     */
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build().parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.debug("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.debug("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.debug("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.debug("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    /**
     * Generates token from username
     */
    public String generateTokenFromUsername(UserDetails userDetails) {
        String email = ((com.andrei.plesoianu.imdbcloneapi.security.services.UserDetailsImpl) userDetails).getEmail();
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }
}
