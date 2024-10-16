package ru.promo.prize_drawing_system.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.promo.prize_drawing_system.persistence.model.PrizeEntity;

@Repository
public interface PrizeRepository extends JpaRepository<PrizeEntity, Long> {
}
