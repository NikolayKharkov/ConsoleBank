package ru.kharkov.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kharkov.enums.ConsoleOperationType;
import ru.kharkov.interfaces.Operation;
import ru.kharkov.services.BankService;
import ru.kharkov.utils.Input;

/**
 * Класс отвечает за списании суммы с счета
 */
@Component
public class AccountWithDrawOperation implements Operation {

    private final String ASK_ACCOUNT_ID = "Enter account ID to withdraw from:";

    private final String ASK_AMOUNT = "Enter amount to withdraw:";

    @Autowired
    private Input input;

    @Autowired
    private BankService bankService;

    @Override
    public void doOperation() {
        int accountId = input.askInt(ASK_ACCOUNT_ID);
        double amount = input.askDouble(ASK_AMOUNT);
        bankService.withdrawAccount(accountId, amount);
        System.out.println(String.format("Amount %s deposited to account ID: %s", amount, accountId));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }
}
