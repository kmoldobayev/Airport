package kg.kuban.airport.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenHandler {
    @Value(value = "${jwt.token.secret}")
    private String secretKey;

    @Value(value = "${jwt.token.expired}")
    private Long jwtTokenLifetime;

    public String generateToken(Authentication authentication) {

        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + this.jwtTokenLifetime);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);

        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())  // Тема токена
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public List<String> getRoleFromToken(String token) {
        return getClaimsFromToken(token).get("roles", List.class);
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secretKey).parse(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("Token expired");
        } catch (MalformedJwtException ex) {
            System.out.println("Token invalid");
        } catch (SignatureException ex) {
            System.out.println("Token signature incorrect");
        } catch (IllegalArgumentException ex) {
            System.out.println("Token must contain claims");

        }
        return false;
    }
}
