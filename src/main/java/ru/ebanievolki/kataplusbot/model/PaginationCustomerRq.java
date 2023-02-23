package ru.ebanievolki.kataplusbot.model;

import lombok.Data;

@Data
public class PaginationCustomerRq {
    private int size;
    private int page;
}
