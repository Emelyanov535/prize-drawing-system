package ru.promo.prize_drawing_system.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(Long id) {
        super(String.format("Event with id [%s] is not found", id));
    }
}
