package ru.kharkov.repositories;

import org.springframework.stereotype.Repository;
import ru.kharkov.dto.AccountEntity;
import ru.kharkov.interfaces.repositories.AccountsRepository;
import ru.kharkov.models.Account;

import java.util.*;

/**
 * Репозиторий для работы с аккаунтами.
 * Хранит данные в памяти.
 */
@Repository
public class AccountsMemoryRepository implements AccountsRepository {

    private int curAccountId;
    private Map<Integer, Account> accounts = new HashMap<>();
    private Map<Integer, List<AccountEntity>> usersAccounts = new HashMap<>();

    @Override
    public Account saveAccount(Account account) {
        account.setId(this.curAccountId);
        this.accounts.put(curAccountId, account);
        int userId = account.getUserId();
        AccountEntity newAccountEntity = new AccountEntity(this.curAccountId);
        List<AccountEntity> accountsDto = this.usersAccounts.get(userId);
        if (Objects.isNull(accountsDto)) {
            accountsDto = new ArrayList<>();
            accountsDto.add(newAccountEntity);
        } else {
            accountsDto.add(newAccountEntity);
        }
        usersAccounts.put(userId, accountsDto);
        this.curAccountId++;
        return account;
    }

    @Override
    public Optional<Account> getAccountById(int id) {
        Account account = this.accounts.get(id);
        return Optional.ofNullable(account);
    }

    @Override
    public Optional<List<Account>> getUserAccounts(int id) {
        List<AccountEntity> accountsDto = this.usersAccounts.get(id);
        if (Objects.isNull(accountsDto)) {
            return Optional.empty();
        }
        List<Account> usersAccounts = new ArrayList<>();
        for (AccountEntity accountEntity : accountsDto) {
            usersAccounts.add(this.accounts.get(accountEntity.getId()));
        }
        return Optional.of(usersAccounts);
    }

    @Override
    public Optional<Account> deleteAccount(int id) {
        Account deletedAccount = this.accounts.get(id);
        this.accounts.remove(id);
        int userId = deletedAccount.getUserId();
        List<AccountEntity> accountsDto = this.usersAccounts.get(userId);
        accountsDto.remove(new AccountEntity(id));
        return Optional.of(deletedAccount);
    }

    @Override
    public Optional<Account> updateAccount(Account account) {
        Account storedAccount = this.accounts.get(account.getId());
        storedAccount.setMoneyAmount(account.getMoneyAmount());
        return Optional.of(storedAccount);
    }
}
