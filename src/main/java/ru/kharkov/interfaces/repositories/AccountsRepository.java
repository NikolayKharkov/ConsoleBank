package ru.kharkov.interfaces.repositories;

import ru.kharkov.models.Account;

import java.util.List;
import java.util.Optional;

/**
 * Интрефейс для репозитория аккаунтов
 */
public interface AccountsRepository {

    /**
     * Метод сохранения нового аккаунта
     * @param account сохраняемый аккаунт
     * @return сохраненный аккаунт с присвоенным ID
     */
    Account saveAccount(Account account);

    /**
     * Метод для получения аккаунта по id
     * @param id аккаунта
     * @return optional искомого аккаунта
     */
    Optional<Account> getAccountById(int id);

    /**
     * Метод для получения списка аккаунтов пользователя
     * @param id пользовталя
     * @return optional список всех аккаунтов пользователя
     */
    Optional<List<Account>> getUserAccounts(int id);

    /**
     * Метод для удаления аккаунта по id
     * @param id аккаунта
     * @return optional удаленного аккаунта
     */
    Optional<Account> deleteAccount(int id);

    /**
     *  Метод для обновления аккаунта.
     * @param account обновляемый аккаунт
     * @return optional аккаунта
     */
    Optional<Account> updateAccount(Account account);
}
