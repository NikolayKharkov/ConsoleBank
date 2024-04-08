package ru.kharkov.interfaces.repositories;

import ru.kharkov.dto.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для хранения пользователей
 */
public interface UsersRepository {

    /**
     * Метод для сохранения пользователя
     * @param userEntity dto пользователя
     * @return userDto с присвоенным id
     */
    UserEntity saveUser(UserEntity userEntity);

    /**
     * Метод для получения пользователя по его id
     * @param id пользователя
     * @return optional userDto
     */
    Optional<UserEntity> getUserById(int id);

    /**
     * Метод для получения всех пользоватлей в системе
     * @return список все пользовательских DTO
     */
    List<UserEntity> getAllUsers();

}
