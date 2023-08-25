package kg.kuban.airport.security;

import kg.kuban.airport.controller.v1.AppUserController;
import kg.kuban.airport.exception.UnauthorizedException;
import kg.kuban.airport.service.AppUserDetailsService;
import kg.kuban.airport.service.impl.AppUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


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
        String token = "";
        try {
          token = this.parseJwt(request);
        } catch (UnauthorizedException ex ) {
            System.out.println("Token not found " +ex);
            logger.info("Token not found " +ex);
        }
        logger.info("Token found=" + token);
        if (Objects.nonNull(token) && this.jwtTokenHandler.validateToken(token)) {
            String username = this.jwtTokenHandler.getUsernameFromToken(token);
            logger.info("doFilterInternal username =" + username);
            UserDetails user = this.appUserDetailsService.loadUserByUsername(username);
            logger.info("doFilterInternal user =" + user.getUsername());
            UsernamePasswordAuthenticationToken authToken =
                    UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities());

            logger.info("doFilterInternal authToken=" + authToken.getName());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) throws UnauthorizedException {
        final String authHeader = request.getHeader("Authorization");
        logger.info("authHeader  = " + authHeader );
        if (Objects.isNull(authHeader)) {
            throw new UnauthorizedException("Header запроса пустой!");
        }

        if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            logger.info("token = " + token);
            if (token == null || token.isEmpty()) {
                throw new UnauthorizedException("Токен пустой!");
            }


            return token;
        }



        return null;
    }

}
