package br.com.ages.adoteumamanha.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static java.lang.Long.parseLong;
import static java.util.Optional.empty;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration}")
    private int jwtExpiration;

    public static String generate(final Long userId, final String jwtSecret, final int jwtExpiration) {

        final Date now = new Date();
        final Date expiryDate = new Date(new Date().getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateToken(final Authentication authentication) {

        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        final Long userId = userPrincipal.getId();

        return generate(userId, jwtSecret, jwtExpiration);
    }

    public Optional<Long> getUserId(final String jwt) {
        try {
            final Claims claims = parse(jwt).getBody();

            return Optional.of(parseLong(claims.getSubject()));
        } catch (Exception ex) {
            return empty();
        }
    }

    private Jws<Claims> parse(final String jwt) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
    }
}
