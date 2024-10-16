package ru.promo.prize_drawing_system.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.promo.prize_drawing_system.persistence.model.EventEntity;
import ru.promo.prize_drawing_system.web.dto.CreateEventDto;

@Component
public class CreateEventDto2EventEntity implements Converter<CreateEventDto, EventEntity> {
    @Override
    public EventEntity convert(CreateEventDto source) {
        return EventEntity.builder()
                .countOfMember(source.getCountOfMember())
                .name(source.getName())
                .dateOfTheDraw(source.getDateOfTheDraw())
                .build();
    }
}
