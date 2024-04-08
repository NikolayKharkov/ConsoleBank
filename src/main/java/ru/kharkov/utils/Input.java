package ru.kharkov.utils;

import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Класс отвечает за считывания данных с консоли.
 */
@Component
public class Input {
    private final Scanner scanner = new Scanner(System.in);

    public String askStr(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    public int askInt(String question) {
        return Integer.parseInt(askStr(question));
    }

    public double askDouble(String question) {
        return Double.parseDouble(askStr(question));
    }
}
