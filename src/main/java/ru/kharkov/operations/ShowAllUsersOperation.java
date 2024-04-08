package ru.kharkov.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kharkov.enums.ConsoleOperationType;
import ru.kharkov.interfaces.Operation;
import ru.kharkov.models.User;
import ru.kharkov.services.BankService;

import java.util.List;

/**
 * Класс отвечает за операцию вывода всех пользователей
 */
@Component
public class ShowAllUsersOperation implements Operation {

    @Autowired
    private BankService bankService;

    @Override
    public void doOperation() {
        List<User> allUsers = bankService.getAllUsers();
        if (allUsers.isEmpty()) {
            System.out.println("No users in system");
        } else {
            System.out.println("List of all users:");
            allUsers.forEach(System.out::println);
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }
}
