package ru.kharkov.interfaces;

import ru.kharkov.enums.ConsoleOperationType;

/**
 * Интерфейс для банковских операций
 */
public interface Operation {

     void doOperation();

     ConsoleOperationType getOperationType();



}
