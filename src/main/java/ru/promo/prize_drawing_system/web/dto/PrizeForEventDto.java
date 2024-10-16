package ru.promo.prize_drawing_system.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrizeForEventDto {
    List<CreatePrizeDto> prizes;
}
