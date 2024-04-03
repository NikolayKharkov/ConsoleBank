package ru.kharkov.models;

import ru.kharkov.dto.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс отображает модель данных Пользователя
 */
public class User {

    private final int id;

    private final String login;

    private List<Account> accountList = new ArrayList<>();

    public User(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.login = userEntity.getLogin();
    }

    public int getId() {
        return id;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public void addAccount(Account account) {
        this.accountList.add(account);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accountList=" + accountList +
                '}';
    }
}

