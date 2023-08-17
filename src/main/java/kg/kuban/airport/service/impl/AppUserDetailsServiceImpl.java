package kg.kuban.airport.service.impl;

import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.repository.AppRoleRepository;
import kg.kuban.airport.repository.AppUserRepository;
import kg.kuban.airport.service.AppUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class AppUserDetailsServiceImpl implements AppUserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private final Logger logger = LoggerFactory.getLogger(AppUserDetailsServiceImpl.class);

    public AppUserDetailsServiceImpl() {
    }

    @Autowired
    public AppUserDetailsServiceImpl(AppUserRepository appUserRepository,
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
