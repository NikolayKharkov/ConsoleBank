package ru.kharkov.interfaces;

import ru.kharkov.entities.AccountEntity;
import ru.kharkov.models.Account;

import java.util.List;

public interface AccountMapper {

     Account toAccountModel(AccountEntity accountEntity);

     AccountEntity toAccountEntity(Account account);

     List<Account> toAccountsModel(List<AccountEntity> accountEntities);

}
