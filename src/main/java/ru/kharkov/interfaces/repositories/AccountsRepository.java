package ru.kharkov.interfaces.repositories;

import ru.kharkov.entities.AccountEntity;

import java.util.List;
import java.util.Optional;

/**
 * Интрефейс для репозитория аккаунтов
 */
public interface AccountsRepository {

    /**
     * Метод сохранения нового аккаунта
     * @param accountEntity сохраняемый аккаунт
     * @return сохраненный аккаунт с присвоенным ID
     */
    AccountEntity saveAccount(AccountEntity accountEntity);

    /**
     * Метод для получения аккаунта по id
     * @param id аккаунта
     * @return optional искомого аккаунта
     */
    Optional<AccountEntity> getAccountById(int id);

    /**
     * Метод для получения списка аккаунтов пользователя
     * @param id пользовталя
     * @return список всех аккаунтов пользователя
     */
    List<AccountEntity> getUserAccounts(int id);

    /**
     * Метод для удаления аккаунта по id
     * @param id аккаунта
     * @return optional удаленного аккаунта
     */
    Optional<AccountEntity> deleteAccount(int id);

    /**
     *  Метод для обновления аккаунта.
     * @param accountEntity обновляемый аккаунт
     * @return optional аккаунта
     */
    AccountEntity updateAccount(AccountEntity accountEntity);
}
