package ru.kharkov.mappers;

import org.springframework.stereotype.Component;
import ru.kharkov.entities.AccountEntity;
import ru.kharkov.entities.UserEntity;
import ru.kharkov.interfaces.AccountMapper;
import ru.kharkov.models.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toAccountModel(AccountEntity accountEntity) {
        return Account
                .builder()
                .id(accountEntity.getId())
                .userId(accountEntity.getUserEntity().getId())
                .moneyAmount(accountEntity.getMoneyAmount())
                .build();
    }


    @Override
    public AccountEntity toAccountEntity(Account account) {
        return AccountEntity
                .builder()
                .id(account.getId())
                .userEntity(UserEntity.builder().id(account.getUserId()).build())
                .moneyAmount(account.getMoneyAmount())
                .build();
    }

    @Override
    public List<Account> toAccountsModel(List<AccountEntity> accountEntities) {
        return Objects.isNull(accountEntities) ? new ArrayList<>() : accountEntities
                .stream()
                .map(this::toAccountModel)
                .collect(Collectors.toList());
    }
}
