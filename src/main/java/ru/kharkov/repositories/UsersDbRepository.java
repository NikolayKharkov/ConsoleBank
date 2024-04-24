package ru.kharkov.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kharkov.entities.UserEntity;
import ru.kharkov.interfaces.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsersDbRepository implements UsersRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(userEntity);
        return userEntity;
    }

    @Transactional
    @Override
    public Optional<UserEntity> getUserById(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        UserEntity userEntity = currentSession.get(UserEntity.class, id);
        return Optional.ofNullable(userEntity);
    }

    @Transactional
    @Override
    public List<UserEntity> getAllUsers() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession
                .createQuery("select ue from UserEntity ue left join fetch ue.accounts")
                .getResultList();
    }
}
