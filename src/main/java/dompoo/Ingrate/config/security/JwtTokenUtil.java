package dompoo.Ingrate.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenUtil {

    private final SecretKey key;
    private final long expiration;
    private final long rememberMeExpiration;

    public JwtTokenUtil(
            @Value("${jwt.secretKey}") String key,
            @Value("${jwt.expiration}") long expiration,
            @Value("${jwt.remembermeExpiration}") long rememberMeExpiration
    ) {
        this.key = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
        this.rememberMeExpiration = rememberMeExpiration;
    }

    public String generateToken(String username, Boolean rememberme) {
        Date now = new Date();
        Date expireDate = rememberme ? new Date(now.getTime() + rememberMeExpiration) : new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(key)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(authToken);
            log.info("JWT Validate 성공");
            return true;
        } catch (Exception ex) {
            // Log or handle JWT exceptions
            log.info("JWT Validate 실패");
            return false;
        }
    }

    //    static public String geneteKey() {
//        SecretKey key = Jwts.SIG.HS512.key().build();
//        return Base64.getEncoder().encodeToString(key.getEncoded());
//    }
}