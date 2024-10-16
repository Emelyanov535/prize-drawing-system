package ru.promo.prize_drawing_system.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventEntityResponse {
    private Long id;

    private String name;

    private Integer available;

    private LocalDateTime dateOfTheDraw;

    private Set<String> prizes;
}
