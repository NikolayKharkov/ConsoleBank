package ru.kharkov.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kharkov.entities.AccountEntity;
import ru.kharkov.interfaces.repositories.AccountsRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class AccountDbRepository implements AccountsRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public AccountEntity saveAccount(AccountEntity accountEntity) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(accountEntity);
        return accountEntity;
    }

    @Override
    @Transactional
    public Optional<AccountEntity> getAccountById(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        AccountEntity accountEntity = currentSession.get(AccountEntity.class, id);
        return Optional.ofNullable(accountEntity);
    }

    @Override
    @Transactional
    public List<AccountEntity> getUserAccounts(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        List<AccountEntity> result = currentSession
                .createQuery("select ae from AccountEntity ae where ae.userEntity.id = :id", AccountEntity.class)
                .setParameter("id", id)
                .getResultList();
        return result;
    }

    @Override
    @Transactional
    public Optional<AccountEntity> deleteAccount(int id) {
        Optional<AccountEntity> accountEntityOptional = getAccountById(id);
        if (accountEntityOptional.isPresent()) {
            Session currentSession = sessionFactory.getCurrentSession();
            currentSession.remove(accountEntityOptional.get());
        }
        return accountEntityOptional;
    }

    @Override
    @Transactional
    public AccountEntity updateAccount(AccountEntity accountEntity) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(accountEntity);
        return accountEntity;
    }
}
