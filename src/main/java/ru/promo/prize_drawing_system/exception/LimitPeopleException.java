package ru.promo.prize_drawing_system.exception;

public class LimitPeopleException extends RuntimeException {
    public LimitPeopleException(String message) {
      super("The number of participants in the event has reached its limit");
    }
}
