package ru.kharkov.utils;

/**
 * Кастомный класс для отлова ошибок в логике сервисов
 */
public class CustomBusinessException extends RuntimeException {

    public CustomBusinessException(String errorMessage) {
        super(errorMessage);
    }
}
