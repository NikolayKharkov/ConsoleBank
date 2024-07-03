package ru.kharkov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kharkov.config.SpringConfig;
import ru.kharkov.view.ConsoleMainMenu;

/**
 * Main класс. Запускает приложение
 */
public class App {

    public static void main( String[] args ) {

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                SpringConfig.class)) {

            context.getBean(ConsoleMainMenu.class).init();
            System.out.println("Hello world!!!");
        }

    }
}
