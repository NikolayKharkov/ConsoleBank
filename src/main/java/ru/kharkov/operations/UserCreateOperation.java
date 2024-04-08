package ru.kharkov.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kharkov.enums.ConsoleOperationType;
import ru.kharkov.interfaces.Operation;
import ru.kharkov.models.User;
import ru.kharkov.services.BankService;
import ru.kharkov.utils.Input;

/**
 * Класс отвечает за операцию создании пользователя.
 */
@Component
public class UserCreateOperation implements Operation {

    private final String CREATE_USER_HEADER = "Enter login for new user:";

    @Autowired
    private Input input;

    @Autowired
    private BankService bankService;

    @Override
    public void doOperation() {
        String newUserLogin = input.askStr(CREATE_USER_HEADER);
        User user = bankService.createUser(newUserLogin);
        System.out.println(String.format("User created: %s", user.toString()));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }


}
