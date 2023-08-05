package kg.kuban.airport.service;

import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.repository.AppRoleRepository;
import kg.kuban.airport.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Component
public class AppUserDetailsService implements UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;

   // private PasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public AppUserDetailsService(AppUserRepository appUserRepository,
                              AppRoleRepository appRoleRepository//,
                              //PasswordEncoder bCryptPasswordEncoder
    ) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        //this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        AppUser appUser = appUserRepository.findByUserLogin(username);
//        if (appUser == null) {
//            throw new UsernameNotFoundException("User not found: " + username);
//        }
//        List<GrantedAuthority> authorities = appUser.getAppRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getTitle()))
//                .collect(Collectors.toList());
//
//        return new AppUser(appUser.getUserLogin(), appUser.getUserPassword(), authorities);
//
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username=" + username);
        Optional<AppUser> appUser = this.appUserRepository.findByUserLogin(username);
        if (appUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        System.out.println(appUser.get());
        return appUser.get();
    }
}
