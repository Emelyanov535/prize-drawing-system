package ru.promo.prize_drawing_system.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.promo.prize_drawing_system.service.AdminService;
import ru.promo.prize_drawing_system.web.dto.CreateEventDto;
import ru.promo.prize_drawing_system.web.dto.PrizeForEventDto;

@RestController
@RequestMapping("api/v1/admin-panel")
@AllArgsConstructor
@Tag(name = "Admin", description = "Управление событиями и призами")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Создание события", description = "Создание нового события для розыгрыша призов", security = @SecurityRequirement(name = "BearerAuth"))
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create-event")
    public ResponseEntity<?> createEvent(
            @RequestBody @Validated CreateEventDto createEventDto) {
        return ResponseEntity.ok(adminService.createEvent(createEventDto));
    }

    @Operation(summary = "Добавление приза к событию", description = "Добавление нового приза к существующему событию", security = @SecurityRequirement(name = "BearerAuth"))
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("add-prize/{id}")
    public ResponseEntity<?> addPrizeToEvent(
            @PathVariable
            @Parameter(description = "ID события, к которому добавляется приз", required = true)
            Long id,
            @RequestBody PrizeForEventDto prizeForEventDto) {
        return ResponseEntity.ok(adminService.addPrizeToEvent(id, prizeForEventDto));
    }
}
