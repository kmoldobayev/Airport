package kg.kuban.airport.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    @Value(value = "${jwt.token.secret}")
    private String secretKey;

    @Value(value = "${jwt.token.expired}")
    private Long jwtTokenLifetime;

    public String generateToken(UserDetails userDetails, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenLifetime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }

    private String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
