package ru.promo.prize_drawing_system.web.dto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventDto {
    @NotBlank(message = "Event name cannot be blank")
    private String name;
    @NotNull(message = "Count Of Member cannot be null")
    @Min(value = 0, message = "Count Of Member must be over 0")
    private Integer countOfMember;
    @NotNull(message = "Date Of The Draw cannot be null")
    @Future(message = "Date Of The Draw must be in the future")
    private LocalDateTime dateOfTheDraw;
}
