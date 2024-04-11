package ru.kharkov.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kharkov.entities.UserEntity;
import ru.kharkov.enums.ConsoleOperationType;
import ru.kharkov.interfaces.Operation;
import ru.kharkov.models.User;
import ru.kharkov.services.BankService;
import ru.kharkov.utils.Input;

/**
 * Класс отвечает за операцию создания аккаунта
 */
@Component
public class AccountCreateOperation implements Operation {

    private final String OPERATION_HEADER = "Enter the user id for which to create an account:";

    private final String SUCCESSFUL_MESSAGE_FORMAT = "New account created with ID: %s for user: %s";

    @Autowired
    private Input input;

    @Autowired
    private BankService bankService;

    @Override
    public void doOperation() {
        int userId = input.askInt(OPERATION_HEADER);
        User user = bankService.createAccount(userId);
        System.out.printf((SUCCESSFUL_MESSAGE_FORMAT) + "%n", user.getId(), user.getLogin());
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
