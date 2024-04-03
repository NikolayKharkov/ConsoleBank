package ru.kharkov.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kharkov.enums.ConsoleOperationType;
import ru.kharkov.interfaces.Operation;
import ru.kharkov.utils.CustomBusinessException;
import ru.kharkov.utils.Input;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Класс отвечает за отображение и логику работы консольного меню
 */
@Component
public class ConsoleMainMenu {

    @Autowired
    private Input input;

    private final Map<ConsoleOperationType, Operation> operations;

    private String mainMenu;

    private static final String EXIT_OPERATION = "EXIT";

    @Autowired
    public ConsoleMainMenu(List<Operation> operations) {
         this.operations = operations.stream()
                 .collect(Collectors.toMap(Operation::getOperationType, Function.identity()));
    }


    public void init() {
        createMainMenu();
        while(true) {
            try {
                String inputOperationName = input.askStr(this.mainMenu);
                if (EXIT_OPERATION.equals(inputOperationName)) {
                    break;
                }
                Operation operation = operations.get(ConsoleOperationType.valueOf(inputOperationName));
                operation.doOperation();
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Not correct input. Expect integer.");
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println("Error. Input not exist operation");
            } catch (CustomBusinessException customBusinessException) {
                System.out.println(customBusinessException.getMessage());
            }
        }
        System.out.println("APPLICATION HAS FINISHED");
    }

    /**
     * Метод отрисует меню выбора операций
     */
    private void createMainMenu() {
        StringBuilder mainMenuBuilder = new StringBuilder();
        String lineSeparator = System.lineSeparator();
        mainMenuBuilder
                .append("Please enter one of operation type:")
                .append(lineSeparator);
        for (ConsoleOperationType operationName : this.operations.keySet()) {
            mainMenuBuilder
                    .append("-")
                    .append(operationName)
                    .append(lineSeparator);
        }
        mainMenuBuilder
                .append("-")
                .append(EXIT_OPERATION)
                .append(lineSeparator);
        this.mainMenu = mainMenuBuilder.toString();
    }
}
