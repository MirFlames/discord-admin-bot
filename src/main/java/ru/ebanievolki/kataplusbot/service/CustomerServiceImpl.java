package ru.ebanievolki.kataplusbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ebanievolki.kataplusbot.model.Customer;
import ru.ebanievolki.kataplusbot.model.PaginationCustomerRq;
import ru.ebanievolki.kataplusbot.model.PaginationDTO;
import ru.ebanievolki.kataplusbot.repository.CustomerRepository;


@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public PaginationDTO<Customer> getAllWithPaging(PaginationCustomerRq paginationCustomerRq) {
        return customerRepository.getAllWithPaging(paginationCustomerRq);
    }
}