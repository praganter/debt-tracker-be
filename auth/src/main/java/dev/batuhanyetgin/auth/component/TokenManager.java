package dev.batuhanyetgin.auth.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class TokenManager {


    private final Date currenTime = new Date(System.currentTimeMillis());
    @Value("${jwt.validity}")
    private String validity;
    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.validity.long}")
    private String validityLong;

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(currenTime)
                .issuer("debtTracker.app")
                .expiration(new Date(currenTime.getTime() + Long.parseLong(validity)))
                .signWith(convertSecretKey(secretKey))
                .compact();

    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(currenTime)
                .issuer("debtTracker.app")
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(validityLong)))
                .signWith(convertSecretKey(secretKey))
                .compact();

    }

    public String getSubject(String token) {
        return parseClaim(token, Claims::getSubject);
    }

    public boolean isExpired(String token) {
        return parseClaim(token, Claims::getExpiration).before(currenTime);
    }


    private SecretKey convertSecretKey(String key) {
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    private <T> T parseClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = Jwts.parser()
                .verifyWith(convertSecretKey(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsTFunction.apply(claims);
    }

}
