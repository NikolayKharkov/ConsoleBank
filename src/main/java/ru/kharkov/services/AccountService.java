package ru.kharkov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kharkov.entities.AccountEntity;
import ru.kharkov.entities.UserEntity;
import ru.kharkov.interfaces.AccountMapper;
import ru.kharkov.interfaces.repositories.AccountsRepository;
import ru.kharkov.mappers.AccountMapperImpl;
import ru.kharkov.models.Account;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для работы с аккаунтами
 */

@Service
public class AccountService {

    @Value("${account.default-amount}")
    private double defaultAmount;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountMapper accountMapper;

    public void createAccount(int userId) {
        UserEntity user = UserEntity
                .builder()
                .id(userId)
                .build();
        AccountEntity newAccount = AccountEntity
                .builder()
                .userEntity(user)
                .moneyAmount(defaultAmount)
                .build();
        this.accountsRepository.saveAccount(newAccount);
    }

    public List<Account> getUserAccount(int id) {
        return this.accountsRepository
                .getUserAccounts(id)
                .stream()
                .map(accountMapper::toAccountModel)
                .collect(Collectors.toList());
    }

    public Account getAccountByIdOrThrow(int id) {
        Optional<AccountEntity> resultOptional = this.accountsRepository.getAccountById(id);
        return resultOptional.map(accountMapper::toAccountModel)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Account with ID %s not found", id)));
    }

    public Account closeAccount(int accountId) {
        Optional<AccountEntity> accountEntity = this.accountsRepository.deleteAccount(accountId);
        return accountEntity.map(accountMapper::toAccountModel)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Account with ID %s not found", accountId)));
    }

    public Account updateAccountOrThrow(Account account) {
        return accountMapper
                .toAccountModel(
                        this.accountsRepository
                                .updateAccount(accountMapper.toAccountEntity(account)));
    }
}
