package ru.ebanievolki.kataplusbot.service;

import ru.ebanievolki.kataplusbot.model.Customer;
import ru.ebanievolki.kataplusbot.model.PaginationCustomerRq;
import ru.ebanievolki.kataplusbot.model.PaginationDTO;

import java.util.List;

public interface CustomerService {

    PaginationDTO<Customer> getAllWithPaging(PaginationCustomerRq paginationCustomerRq);
}
