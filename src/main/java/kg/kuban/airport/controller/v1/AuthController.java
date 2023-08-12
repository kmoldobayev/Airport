package kg.kuban.airport.controller.v1;

import kg.kuban.airport.dto.AppUserRequestDto;
import kg.kuban.airport.dto.AuthDto;
import kg.kuban.airport.dto.TokenResponseDto;
import kg.kuban.airport.exception.InvalidCredentialsException;
import kg.kuban.airport.exception.LogoutException;
import kg.kuban.airport.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 Контроллер по авторизации:
 1.	Метод Логина в систему
 2.	Метод Выхода логина из системы
 */


@RestController
@RequestMapping(value = "/v1/auth")
public class AuthController {
    private AuthService authService;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(
            @RequestBody AuthDto authDto
    ) throws InvalidCredentialsException {
        TokenResponseDto tokenResponseDto;

        logger.info("authDto login=" + authDto.getLogin());
        logger.info("authDto password=" + authDto.getPassword());
        this.authService.login(authDto.getLogin(), authDto.getPassword());
        tokenResponseDto = authService.loginUser(authDto.getLogin(), authDto.getPassword());
        return ResponseEntity.ok(tokenResponseDto);

    }



//    @PostMapping(value = "/logout")
//    public ResponseEntity<?>  logout() {
//        try {
//            String username = "";
//            Authentication authentication = this.authService.getSecurityContext().getAuthentication();
//            if (Objects.nonNull(authentication)) {
//                username = authentication.getName();
//            }
//            this.authService.logout();
//            return ResponseEntity.ok("Пользователь " + username + " вышел из системы");
//        } catch (LogoutException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
}
