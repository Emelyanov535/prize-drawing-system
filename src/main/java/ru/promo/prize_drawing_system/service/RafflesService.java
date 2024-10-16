package ru.promo.prize_drawing_system.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.promo.prize_drawing_system.exception.EventNotFoundException;
import ru.promo.prize_drawing_system.exception.LimitPeopleException;
import ru.promo.prize_drawing_system.exception.UserAlreadyRegisterOnRaffle;
import ru.promo.prize_drawing_system.exception.UserNotFoundException;
import ru.promo.prize_drawing_system.persistence.model.EventEntity;
import ru.promo.prize_drawing_system.persistence.model.PrizeEntity;
import ru.promo.prize_drawing_system.persistence.model.UserEntity;
import ru.promo.prize_drawing_system.persistence.repository.EventRepository;
import ru.promo.prize_drawing_system.persistence.repository.PrizeRepository;
import ru.promo.prize_drawing_system.persistence.repository.UserRepository;
import ru.promo.prize_drawing_system.web.dto.EventEntityResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RafflesService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final PrizeRepository prizeRepository;
    private final Random random = new Random();


    @Transactional(readOnly = true)
    public List<EventEntityResponse> getEvents(){
        List<EventEntity> eventEntities = eventRepository.findAll();

        Map<Long, Integer> availableMap = eventEntities.stream()
                .collect(Collectors.toMap(
                        EventEntity::getId,
                        eventEntity -> eventEntity.getCountOfMember() - eventEntity.getUsers().size()
                ));

        return eventEntities.stream()
                .filter(eventEntity -> eventEntity.getDateOfTheDraw().isAfter(LocalDateTime.now()))
                .map(eventEntity -> EventEntityResponse.builder()
                        .id(eventEntity.getId())
                        .name(eventEntity.getName())
                        .dateOfTheDraw(eventEntity.getDateOfTheDraw())
                        .available(availableMap.get(eventEntity.getId()))
                        .prizes(eventEntity.getPrizes().stream()
                                .map(PrizeEntity::getName)
                                .collect(Collectors.toSet()))
                        .build()
                ).toList();
    }

    @Transactional
    public void registerOnRaffles(Long eventId) {
        // Получаем текущего пользователя по имени
        final String username = authService.getAuthInfo().getUsername();
        final UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        // Получаем событие по идентификатору
        final EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        // Првоеряем что пользователь еще не учавствует в розыгрыше
        if (eventEntity.getUsers().contains(user)) {
            throw new UserAlreadyRegisterOnRaffle(username);
        }

        // Проверка, что количество участников не превышает допустимый порог
        if (eventEntity.getUsers().size() >= eventEntity.getCountOfMember()) {
            throw new LimitPeopleException("");
        }

        eventEntity.getUsers().add(user);
        user.getEvents().add(eventEntity);

        eventRepository.save(eventEntity);
        userRepository.save(user);
    }

    @Transactional
    public Map<String, String> drawPrizesForEvent(Long eventId) {
        EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        Set<UserEntity> users = eventEntity.getUsers();
        List<PrizeEntity> availablePrizes = eventEntity.getPrizes().stream()
                .filter(prize -> prize.getMaxQuantity() > 0)
                .toList();

        Map<String, String> winnersMap = new HashMap<>();

        if (availablePrizes.isEmpty()) {
            return winnersMap;
        }

        // Список для хранения призов, которые еще могут быть выданы
        List<PrizeEntity> remainingPrizes = new ArrayList<>(availablePrizes);

        // Генерируем случайные числа для пользователей и назначаем призы
        users.forEach(user -> {
            int randomValue = random.nextInt(100) + 1;

            // Находим приз по вероятности
            Optional<PrizeEntity> optionalPrize = remainingPrizes.stream()
                    .filter(prize -> randomValue <= prize.getProbability() && prize.getMaxQuantity() > 0)
                    .findFirst();

            optionalPrize.ifPresent(prize -> {
                // Уменьшаем количество доступных призов
                prize.setMaxQuantity(prize.getMaxQuantity() - 1);
                prizeRepository.save(prize);

                // Добавляем победителя в результат
                winnersMap.put(user.getUsername(), prize.getName());

                // Если призы кончились, убираем его из доступных
                if (prize.getMaxQuantity() == 0) {
                    remainingPrizes.remove(prize);
                }
            });
        });

        return winnersMap;
    }
}
