package ru.promo.prize_drawing_system.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.promo.prize_drawing_system.service.AuthService;
import ru.promo.prize_drawing_system.web.dto.JwtRequest;
import ru.promo.prize_drawing_system.web.dto.JwtResponse;
import ru.promo.prize_drawing_system.web.dto.RefreshJwtRequest;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Аутентификация и управление токенами")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Вход пользователя", description = "Авторизация пользователя и получение токенов")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody @Validated JwtRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }

    @Operation(summary = "Получение нового токена доступа", description = "Обновление токена доступа по refresh токену")
    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(
            @RequestBody @Validated RefreshJwtRequest request) {
        return ResponseEntity.ok(authService.getAccessToken(request.getRefreshToken()));
    }

    @Operation(summary = "Обновление refresh токена", description = "Обновление refresh токена по существующему refresh токену", security = @SecurityRequirement(name = "BearerAuth"))
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(
            @RequestBody @Validated RefreshJwtRequest request) {
        return ResponseEntity.ok(authService.refresh(request.getRefreshToken()));
    }
}
