package ru.ebanievolki.kataplusbot.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginationDTO<T> {
    private List<T> pageableElements;
    private final int countElements;
    private final int countPages;
    private final long pageNumber;
}
