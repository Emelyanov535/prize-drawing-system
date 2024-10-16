package ru.promo.prize_drawing_system.persistence.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }
}
