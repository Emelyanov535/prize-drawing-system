package ru.promo.prize_drawing_system.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.promo.prize_drawing_system.exception.EventNotFoundException;
import ru.promo.prize_drawing_system.persistence.model.EventEntity;
import ru.promo.prize_drawing_system.persistence.model.PrizeEntity;
import ru.promo.prize_drawing_system.persistence.repository.EventRepository;
import ru.promo.prize_drawing_system.persistence.repository.PrizeRepository;
import ru.promo.prize_drawing_system.web.dto.CreateEventDto;
import ru.promo.prize_drawing_system.web.dto.PrizeForEventDto;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private final EventRepository eventRepository;
    private final PrizeRepository prizeRepository;
    private final ConversionService conversionService;
    private final SchedulerService schedulerService;

    @Transactional
    public Long createEvent(CreateEventDto createEventDto) {
        EventEntity eventEntity = conversionService.convert(createEventDto, EventEntity.class);

        eventRepository.save(eventEntity);
        schedulerService.scheduleEventExecution(eventEntity);

        return eventEntity.getId();
    }

    @Transactional
    public Long addPrizeToEvent(Long eventId, PrizeForEventDto prizeForEventDto) {
        EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        List<PrizeEntity> newPrizes = prizeForEventDto.getPrizes().stream()
                .map(prize -> PrizeEntity.builder()
                        .event(eventEntity)
                        .name(prize.getName())
                        .probability(prize.getProbability())
                        .maxQuantity(prize.getMaxQuantity())
                        .build())
                .toList();

        prizeRepository.saveAll(newPrizes);

        return eventId;
    }
}
