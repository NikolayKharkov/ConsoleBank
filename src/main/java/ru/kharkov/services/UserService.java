package ru.kharkov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kharkov.entities.UserEntity;
import ru.kharkov.interfaces.UserMapper;
import ru.kharkov.interfaces.repositories.UsersRepository;
import ru.kharkov.mappers.UserMapperImpl;
import ru.kharkov.models.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для работы с пользователями
 */

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserMapper userMapper;

    public User createUser(String userLogin) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(userLogin);
        UserEntity result = this.usersRepository.saveUser(userEntity);
        return userMapper.toUserModel(result);
    }

    public List<User> getAllUsers() {
        return this.usersRepository
                .getAllUsers()
                .stream()
                .map(userMapper::toUserModel)
                .collect(Collectors.toList());
    }

    public User getUserByIdOrThrow(int id) {
        Optional<UserEntity> resultOptional = this.usersRepository.getUserById(id);
        return resultOptional.map(userMapper::toUserModel)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Account with ID %s not found", id)));
    }
}
