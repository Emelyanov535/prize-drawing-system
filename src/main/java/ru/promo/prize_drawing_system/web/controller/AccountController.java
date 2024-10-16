package ru.promo.prize_drawing_system.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.promo.prize_drawing_system.service.AccountService;
import ru.promo.prize_drawing_system.web.dto.RegisterDto;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
@Tag(name = "Account", description = "Управление аккаунтами пользователей")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Регистрация пользователя", description = "Регистрация нового пользователя в системе")
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Validated RegisterDto registerDto) {
        return ResponseEntity.ok(accountService.register(registerDto));
    }
}
