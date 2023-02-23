package ru.ebanievolki.kataplusbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.ebanievolki.kataplusbot.model.Customer;
import ru.ebanievolki.kataplusbot.model.PaginationCustomerRq;
import ru.ebanievolki.kataplusbot.model.PaginationDTO;
import ru.ebanievolki.kataplusbot.service.CustomerService;

@RestController
@RequiredArgsConstructor
public class adminController {
    CustomerService customerService;

    public ResponseEntity<PaginationDTO<Customer>> getAllCustomers(PaginationCustomerRq paginationCustomerRq) {
        return ResponseEntity.ok(customerService.getAllWithPaging(paginationCustomerRq));
    }
}
