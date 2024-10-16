package ru.promo.prize_drawing_system.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.promo.prize_drawing_system.persistence.model.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
