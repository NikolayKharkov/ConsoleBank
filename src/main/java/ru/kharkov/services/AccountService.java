package ru.kharkov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kharkov.interfaces.repositories.AccountsRepository;
import ru.kharkov.models.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с аккаунтами
 */

@Service
public class AccountService {

    @Autowired
    private AccountsRepository accountsRepository;

    public void createAccount(Account account) {
        this.accountsRepository.saveAccount(account);
    }

    public List<Account> getUserAccount(int id) {
        return this.accountsRepository
                .getUserAccounts(id)
                .orElse(new ArrayList<>());
    }

    public Optional<Account> getAccountById(int id) {
        return this.accountsRepository.getAccountById(id);
    }

    public Optional<Account> closeAccount(int accountId) {
        return this.accountsRepository.deleteAccount(accountId);
    }

    public Optional<Account> updateAccount(Account account) {
        return this.accountsRepository.updateAccount(account);
    }


}
