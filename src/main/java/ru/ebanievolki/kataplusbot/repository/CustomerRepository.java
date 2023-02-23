package ru.ebanievolki.kataplusbot.repository;


import ru.ebanievolki.kataplusbot.model.Customer;
import ru.ebanievolki.kataplusbot.model.PaginationCustomerRq;
import ru.ebanievolki.kataplusbot.model.PaginationDTO;

import java.util.List;


public interface CustomerRepository {

    List<Customer> getStats();

    void addCustomer(Customer customer);

    void updateCustomer(Customer customer);

    boolean isExist(Long discordId);

    Customer getCustomer(Long discordId);

    PaginationDTO<Customer> getAllWithPaging(PaginationCustomerRq paginationCustomerRq);

}
