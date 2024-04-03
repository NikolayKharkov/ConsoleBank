package ru.kharkov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kharkov.dto.UserEntity;
import ru.kharkov.interfaces.repositories.UsersRepository;
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

    public UserEntity createUser(String userLogin) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(userLogin);
        return this.usersRepository.saveUser(userEntity);
    }

    public List<User> getAllUsers() {
        return this.usersRepository
                .getAllUsers()
                .stream()
                .map(User::new)
                .collect(Collectors.toList());
    }

    public Optional<UserEntity> getUserById(int id) {
        return this.usersRepository.getUserById(id);
    }
}
