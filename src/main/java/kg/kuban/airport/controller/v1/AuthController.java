package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.dto.AuthDto;
import kg.kuban.airport.dto.TokenResponseDto;
import kg.kuban.airport.exception.InvalidCredentialsException;
import kg.kuban.airport.service.impl.AuthServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 Контроллер по аутентификации:
 1.	Метод Логина в систему
 2.	Метод Выхода логина из системы
 */


@RestController
@RequestMapping(value = "/v1/auth")
@Tag(
        name = "Контроллер для аутентификации пользователей системы",
        description = "Описывает точки доступа по аутентификации пользователей системы"
)
public class AuthController {
    private AuthServiceImpl authServiceImpl;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthServiceImpl authServiceImpl) {
        this.authServiceImpl = authServiceImpl;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(
            @RequestBody AuthDto authDto
    ) throws InvalidCredentialsException {
        TokenResponseDto tokenResponseDto;

        logger.info("authDto login=" + authDto.getLogin());
        logger.info("authDto password=" + authDto.getPassword());
        this.authServiceImpl.login(authDto.getLogin(), authDto.getPassword());
        tokenResponseDto = authServiceImpl.loginUser(authDto.getLogin(), authDto.getPassword());
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
