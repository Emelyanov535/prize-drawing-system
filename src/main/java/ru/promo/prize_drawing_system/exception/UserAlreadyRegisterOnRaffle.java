package ru.promo.prize_drawing_system.exception;

public class UserAlreadyRegisterOnRaffle extends RuntimeException {
    public UserAlreadyRegisterOnRaffle(String username) {
      super(String.format("User with username [%s] already register on raffle", username));
    }
}
