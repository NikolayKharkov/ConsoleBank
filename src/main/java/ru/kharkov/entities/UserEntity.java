package ru.kharkov.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_login")
    private String login;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    private List<AccountEntity> accounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity userEntity = (UserEntity) o;

        return id == userEntity.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
