package ru.kharkov.repositories;

import org.springframework.stereotype.Repository;
import ru.kharkov.dto.UserEntity;
import ru.kharkov.interfaces.repositories.UsersRepository;

import java.util.*;

/**
 * Репозиторий для работы с пользователями.
 * Хранит данные в памяти.
 */
@Repository
public class UsersMemoryRepository implements UsersRepository {

    private int curUserId;

    private Map<Integer, UserEntity> userDtoHashMap = new LinkedHashMap<>();

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        userEntity.setId(curUserId);
        userDtoHashMap.put(curUserId, userEntity);
        curUserId++;
        return userEntity;
    }

    @Override
    public Optional<UserEntity> getUserById(int id) {
        UserEntity userEntity = this.userDtoHashMap.get(id);
        return Objects.isNull(userEntity) ? Optional.empty() : Optional.of(userEntity);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return this.userDtoHashMap.values().stream().toList();
    }
}
