package ru.kharkov.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kharkov.entities.UserEntity;
import ru.kharkov.interfaces.AccountMapper;
import ru.kharkov.interfaces.UserMapper;
import ru.kharkov.models.User;

/**
 * Класс для мапинга сущности User
 */
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public User toUserModel(UserEntity userEntity) {
        return User
                .builder()
                .id(userEntity.getId())
                .login(userEntity.getLogin())
                .accounts(accountMapper.toAccountsModel(userEntity.getAccounts()))
                .build();
    }
}
