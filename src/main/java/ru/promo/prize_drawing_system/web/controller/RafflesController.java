package ru.promo.prize_drawing_system.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.promo.prize_drawing_system.service.RafflesService;
import ru.promo.prize_drawing_system.web.dto.EventEntityResponse;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/raffles")
@Tag(name = "Raffles", description = "Управление розыгрышами")
public class RafflesController {
    private final RafflesService rafflesService;

    @Operation(summary = "Получение списка розыгрышей", description = "Получение списка всех доступных розыгрышей")
    @GetMapping("/list")
    public ResponseEntity<List<EventEntityResponse>> getAllRaffles() {
        return ResponseEntity.ok(rafflesService.getEvents());
    }

    @Operation(summary = "Регистрация на розыгрыш", description = "Регистрация пользователя на участие в розыгрыше по ID события", security = @SecurityRequirement(name = "BearerAuth"))
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/register-on-raffles/{id}")
    public void registerOnRaffles(
            @PathVariable
            @Parameter(description = "ID розыгрыша для регистрации", required = true)
            Long id) {
        rafflesService.registerOnRaffles(id);
    }
}
