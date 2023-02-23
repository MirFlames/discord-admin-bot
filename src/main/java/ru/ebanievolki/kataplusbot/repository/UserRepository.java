package ru.ebanievolki.kataplusbot.repository;


import ru.ebanievolki.kataplusbot.model.User;

public interface UserRepository {

    User getUserByName(String username);
}
