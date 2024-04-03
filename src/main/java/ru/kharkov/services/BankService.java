package ru.kharkov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kharkov.dto.AccountEntity;
import ru.kharkov.dto.UserEntity;
import ru.kharkov.models.Account;
import ru.kharkov.models.User;
import ru.kharkov.utils.CustomBusinessException;

import java.util.List;
import java.util.Optional;

/**
 * Сервис отвечающий за общую логику работы банка.
 * Содержит в себе AccountService и UserService
 */
@Component
public class BankService {

    @Value("${account.default-amount}")
    private double defaultAmount;

    @Value("${account.transfer-commission}")
    private double transferCommission;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    public User createUser(String userLogin) {
        UserEntity newUserEntity = this.userService.createUser(userLogin);
        Account newAccount = new Account();
        newAccount.setMoneyAmount(defaultAmount);
        newAccount.setUserId(newUserEntity.getId());
        accountService.createAccount(newAccount);
        User result = new User(newUserEntity);
        result.addAccount(newAccount);
        return result;
    }

    public List<User> getAllUsers() {
        List<User> allUsers = this.userService.getAllUsers();
        for (User user : allUsers) {
            user.setAccountList(this.accountService.getUserAccount(user.getId()));
        }
        return allUsers;
    }

    public UserEntity createAccount(int id) {
        Optional<UserEntity> optionalUserDto = this.userService.getUserById(id);
        UserEntity userEntity = optionalUserDto
                .orElseThrow(() -> new CustomBusinessException(String.format("User with ID %s not found", id)));
        Account newAccount = new Account();
        newAccount.setUserId(userEntity.getId());
        newAccount.setMoneyAmount(this.defaultAmount);
        this.accountService.createAccount(newAccount);
        return userEntity;
    }

    public AccountEntity closeAccount(int accountId) {
        Account closingAccount = getAccountByIdOrThrow(accountId);
        List<Account> userAccounts = this.accountService.getUserAccount(closingAccount.getUserId());
        if (userAccounts.isEmpty() || userAccounts.size() == 1) {
            throw new CustomBusinessException(
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
        this.accountService.updateAccount(eldestAccount);

        return new AccountEntity(accountId);
    }


    public AccountEntity depositAccount(int accountId, double amount) {
        Account toDepositAccount = getAccountByIdOrThrow(accountId);
        double curSum = toDepositAccount.getMoneyAmount();
        double newSum = curSum + amount;
        toDepositAccount.setMoneyAmount(newSum);
        this.accountService.updateAccount(toDepositAccount);
        return new AccountEntity(accountId);
    }

    public AccountEntity withDrawAccount(int accountId, double amount) {
        Account withDrawAccount = getAccountByIdOrThrow(accountId);
        double curSum = withDrawAccount.getMoneyAmount();
        double newSum = curSum - amount;
        if (newSum < 0) {
            throw new CustomBusinessException(
                    String.format(
                            "Error! No such money to withdraw from account: %s, moneyAmount=%s, attemptedWithdraw=%s",
                            accountId, amount, curSum));
        }
        withDrawAccount.setMoneyAmount(newSum);
        this.accountService.updateAccount(withDrawAccount);
        return new AccountEntity(accountId);
    }

    public void transferAmount(int accountIdSource, int accountIdTarget, double amount) {
        Account sourceAccount = getAccountByIdOrThrow(accountIdSource);
        Account targetAccount = getAccountByIdOrThrow(accountIdTarget);
        double curSourceAmount = sourceAccount.getMoneyAmount();
        double curTargetAmount = targetAccount.getMoneyAmount();
        double transferSumWithCommission =
                sourceAccount.getUserId() == targetAccount.getUserId() ?
                        amount : amount + (amount * this.transferCommission);
        if (curSourceAmount < transferSumWithCommission) {
            throw new CustomBusinessException("Source account not has enough sum");
        }
        sourceAccount.setMoneyAmount(curSourceAmount - transferSumWithCommission);
        targetAccount.setMoneyAmount(curTargetAmount + amount);
        this.accountService.updateAccount(sourceAccount);
        this.accountService.updateAccount(targetAccount);
    }



    private Account getAccountByIdOrThrow(int accountId) {
        Optional<Account> resultOptional = this.accountService.getAccountById(accountId);
        return resultOptional
                .orElseThrow(() -> new CustomBusinessException(String.format("Account with ID %s not found", accountId)));
    }

}
