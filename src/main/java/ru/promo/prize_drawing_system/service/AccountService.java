package ru.promo.prize_drawing_system.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.promo.prize_drawing_system.persistence.model.Role;
import ru.promo.prize_drawing_system.persistence.model.UserEntity;
import ru.promo.prize_drawing_system.persistence.repository.UserRepository;
import ru.promo.prize_drawing_system.web.dto.RegisterDto;

import java.util.Set;

@Service
@AllArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long register(RegisterDto registerDto) {
        UserEntity userEntity = UserEntity.builder()
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .roles(Set.of(Role.USER))
                .build();

        return userRepository.save(userEntity).getId();
    }
}
