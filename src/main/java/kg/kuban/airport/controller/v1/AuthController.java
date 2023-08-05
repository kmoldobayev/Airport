package kg.kuban.airport.controller.v1;

import kg.kuban.airport.dto.AuthDto;
import kg.kuban.airport.dto.TokenResponseDto;
import kg.kuban.airport.exception.InvalidCredentialsException;
import kg.kuban.airport.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(
            @RequestBody AuthDto authDto
    ) throws InvalidCredentialsException {
        TokenResponseDto tokenResponseDto;

        System.out.println("authDto login=" + authDto.getLogin());
        System.out.println("authDto password=" + authDto.getPassword());
        this.authService.login(authDto.getLogin(), authDto.getPassword());
        tokenResponseDto = authService.loginUser(authDto.getLogin(), authDto.getPassword());
        return ResponseEntity.ok(tokenResponseDto);

    }

    @PostMapping(value = "/logout")
    public String logout() {
        try {
            this.authService.logout();
            return "OK";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
