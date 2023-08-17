package kg.kuban.airport.security;

import kg.kuban.airport.service.AppUserDetailsService;
import kg.kuban.airport.service.impl.AppUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenHandler jwtTokenHandler;

    private final AppUserDetailsService appUserDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenHandler jwtTokenHandler, AppUserDetailsService appUserDetailsService) {
        this.jwtTokenHandler = jwtTokenHandler;
        this.appUserDetailsService = appUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {

        String token = this.parseJwt(request);

        if (Objects.nonNull(token) && this.jwtTokenHandler.validateToken(token)) {
            String username = this.jwtTokenHandler.getUsernameFromToken(token);
            UserDetails user = this.appUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken =
                    UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

}
