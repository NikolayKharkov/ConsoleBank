package ru.kharkov.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kharkov.enums.ConsoleOperationType;
import ru.kharkov.interfaces.Operation;
import ru.kharkov.services.BankService;
import ru.kharkov.utils.Input;

/**
 * Класс для операции перевода с счета на счет
 */
@Component
public class AccountTransferOperation implements Operation {

    private final String ASK_SOURCE_ACCOUNT_ID = "Enter source account ID:";
    private final String ASK_TARGET_ACCOUNT_ID = "Enter target  account ID:";
    private final String ASK_AMOUNT = "Enter amount to transfer:";

    @Autowired
    private Input input;

    @Autowired
    private BankService bankService;

    @Override
    public void doOperation() {
        int sourceAccountId = input.askInt(ASK_SOURCE_ACCOUNT_ID);
        int targetAccountId = input.askInt(ASK_TARGET_ACCOUNT_ID);
        double amount = input.askDouble(ASK_AMOUNT);
        bankService.transferAmount(sourceAccountId, targetAccountId, amount);
        System.out.println(
                String.format(
                        "Amount %s transferred from account ID %s to account ID %s."
                        , amount, sourceAccountId, targetAccountId));
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
