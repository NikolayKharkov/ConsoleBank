package ru.kharkov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kharkov.models.Account;
import ru.kharkov.models.User;

import java.util.List;

/**
 * Сервис отвечающий за общую логику работы банка.
 * Содержит в себе AccountService и UserService
 */
@Service
public class BankService {

    @Value("${account.transfer-commission}")
    private double transferCommission;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Transactional
    public User createUser(String userLogin) {
        User result = this.userService.createUser(userLogin);
        accountService.createAccount(result.getId());
        return result;
    }

    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    public User createAccount(int userId) {
        User result = this.userService.getUserByIdOrThrow(userId);
        this.accountService.createAccount(userId);
        return result;
    }

    @Transactional
    public Account closeAccount(int accountId) {
        Account closingAccount = this.accountService.getAccountByIdOrThrow(accountId);
        List<Account> userAccounts = this.accountService.getUserAccount(closingAccount.getUserId());
        if (userAccounts.isEmpty() || userAccounts.size() == 1) {
            throw new IllegalArgumentException(
                    String.format("Not possible to delete account. User not has or only has one account", accountId));
        }

        Account eldestAccount = null;

        for (int i = 0; i < userAccounts.size(); i++) {
            if (!userAccounts.get(i).equals(closingAccount)) {
                eldestAccount = userAccounts.get(i);
                break;
            }
        }

        double firstAccountAmount = eldestAccount.getMoneyAmount();
        double closingAccountAmount = closingAccount.getMoneyAmount();
        eldestAccount.setMoneyAmount(firstAccountAmount + closingAccountAmount);

        this.accountService.closeAccount(closingAccount.getId());
        this.accountService.updateAccountOrThrow(eldestAccount);

        return closingAccount;
    }


    public Account depositAccount(int accountId, double amount) {
        checkSumNotNegative(amount);
        Account toDepositAccount = this.accountService.getAccountByIdOrThrow(accountId);
        double curSum = toDepositAccount.getMoneyAmount();
        double newSum = curSum + amount;
        toDepositAccount.setMoneyAmount(newSum);
        this.accountService.updateAccountOrThrow(toDepositAccount);
        return toDepositAccount;
    }

    public Account withdrawAccount(int accountId, double amount) {
        checkSumNotNegative(amount);
        Account withDrawAccount = this.accountService.getAccountByIdOrThrow(accountId);
        double curSum = withDrawAccount.getMoneyAmount();
        double newSum = curSum - amount;
        if (newSum < 0) {
            throw new IllegalArgumentException(
                    String.format(
                            "Error! No such money to withdraw from account: %s, moneyAmount=%s, attemptedWithdraw=%s",
                            accountId, amount, curSum));
        }
        withDrawAccount.setMoneyAmount(newSum);
        return this.accountService.updateAccountOrThrow(withDrawAccount);
    }

    @Transactional
    public void transferAmount(int accountIdSource, int accountIdTarget, double amount) {
        checkSumNotNegative(amount);
        Account sourceAccount = this.accountService.getAccountByIdOrThrow(accountIdSource);
        Account targetAccount = this.accountService.getAccountByIdOrThrow(accountIdTarget);
        double curSourceAmount = sourceAccount.getMoneyAmount();
        double curTargetAmount = targetAccount.getMoneyAmount();
        double transferSumWithCommission =
                sourceAccount.getUserId() == targetAccount.getUserId() ?
                        amount : amount + (amount * this.transferCommission);
        if (curSourceAmount < transferSumWithCommission) {
            throw new IllegalArgumentException("Source account not has enough sum");
        }
        sourceAccount.setMoneyAmount(curSourceAmount - transferSumWithCommission);
        targetAccount.setMoneyAmount(curTargetAmount + amount);
        this.accountService.updateAccountOrThrow(sourceAccount);
        this.accountService.updateAccountOrThrow(targetAccount);
    }

    private void checkSumNotNegative(double sum) {
        if (sum < 0) {
            throw new IllegalArgumentException("Sum can't be below zero");
        }
    }
}
