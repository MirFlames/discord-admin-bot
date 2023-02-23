package ru.ebanievolki.kataplusbot.repository;

import org.springframework.stereotype.Repository;
import ru.ebanievolki.kataplusbot.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUserByName(String username) {
        return entityManager.createQuery("from User where name = :name", User.class)
                .setParameter("name", username)
                .getSingleResult();
    }
}
