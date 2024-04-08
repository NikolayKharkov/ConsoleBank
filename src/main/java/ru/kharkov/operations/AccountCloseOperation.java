package ru.kharkov.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kharkov.dto.AccountEntity;
import ru.kharkov.enums.ConsoleOperationType;
import ru.kharkov.interfaces.Operation;
import ru.kharkov.services.BankService;
import ru.kharkov.utils.Input;

/**
 * Класс отвечает за операцию закрытия счета
 */
@Component
public class AccountCloseOperation implements Operation {

    private final String CLOSE_ACCOUNT_HEADER = "Enter account ID to close:";

    @Autowired
    private Input input;

    @Autowired
    private BankService bankService;

    @Override
    public void doOperation() {
        int accountId = input.askInt(CLOSE_ACCOUNT_HEADER);
        AccountEntity accountEntity = bankService.closeAccount(accountId);
        System.out.println(String.format("Account %s has been closed.", accountEntity.toString()));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
