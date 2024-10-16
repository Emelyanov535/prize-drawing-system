package ru.promo.prize_drawing_system.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super(String.format("User with username [%s] is not found", username));
    }
}
