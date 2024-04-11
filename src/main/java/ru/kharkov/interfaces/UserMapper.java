package ru.kharkov.interfaces;

import ru.kharkov.entities.UserEntity;
import ru.kharkov.models.User;

public interface UserMapper {

    User toUserModel(UserEntity userEntity);

}
