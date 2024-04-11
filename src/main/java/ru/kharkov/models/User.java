package ru.kharkov.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс отображает модель данных Пользователя
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private int id;

    private String login;

    private List<Account> accounts = new ArrayList<>();

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
                ", accountList=" + accounts +
                '}';
    }
}

