package ru.promo.prize_drawing_system.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJwtRequest {
    @NotBlank(message = "Refresh token cannot be blank")
    public String refreshToken;
}
