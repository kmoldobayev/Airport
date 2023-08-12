package kg.kuban.airport.service;

import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.repository.AppRoleRepository;
import kg.kuban.airport.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class AppUserDetailsService implements UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private final Logger logger = LoggerFactory.getLogger(AppUserDetailsService.class);


    @Autowired
    public AppUserDetailsService(AppUserRepository appUserRepository,
                              AppRoleRepository appRoleRepository

    ) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("username=" + username);
        Optional<AppUser> appUser = this.appUserRepository.findByUserLogin(username);
        if (appUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        logger.info(appUser.get().getUserLogin());
        return appUser.get();
    }
}
