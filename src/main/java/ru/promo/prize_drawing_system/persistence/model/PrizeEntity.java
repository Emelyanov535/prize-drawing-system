package ru.promo.prize_drawing_system.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_prize")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrizeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer probability; // Вероятность выпадения

    @Column(nullable = false)
    private Integer maxQuantity; // Максимальное количество

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;
}
