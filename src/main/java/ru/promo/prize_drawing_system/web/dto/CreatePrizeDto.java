package ru.promo.prize_drawing_system.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePrizeDto {
    @NotBlank(message = "Prize name cannot be blank")
    private String name;
    @Min(1)
    private Integer probability;
    @Min(1)
    private Integer maxQuantity;
}
