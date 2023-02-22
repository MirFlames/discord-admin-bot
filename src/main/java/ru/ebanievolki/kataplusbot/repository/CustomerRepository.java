package ru.ebanievolki.kataplusbot.repository;


import ru.ebanievolki.kataplusbot.model.Customer;

import java.util.List;


public interface CustomerRepository {

    List<Customer> getStats();

    void addCustomer(Customer customer);

    void updateCustomer(Customer customer);

    boolean isExist(Long discordId);

    Customer getCustomer(Long discordId);

}
