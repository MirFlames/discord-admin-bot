package ru.ebanievolki.kataplusbot.repository;

import javax.persistence.*;
import org.springframework.stereotype.Repository;
import ru.ebanievolki.kataplusbot.model.Customer;
import ru.ebanievolki.kataplusbot.model.PaginationCustomerRq;
import ru.ebanievolki.kataplusbot.model.PaginationDTO;

import java.util.List;


@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Customer> getStats() {
        return entityManager.createQuery("from Customer order by plusCount desc", Customer.class)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public void addCustomer(Customer customer) {
        entityManager.persist(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        entityManager.merge(customer);
    }

    @Override
    public boolean isExist(Long id) {
        return !entityManager.createQuery("from Customer where id = :id", Customer.class)
                .setParameter("id", id)
                .getResultList().isEmpty();
    }

    @Override
    public Customer getCustomer(Long id) {
        return entityManager.createQuery("from Customer where id = :id", Customer.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public PaginationDTO<Customer> getAllWithPaging(PaginationCustomerRq paginationCustomerRq) {
        return PaginationDTO.builder()
                .pageableElements(entityManager.createQuery("from Customer")
                        .setFirstResult(paginationCustomerRq.getPage() * paginationCustomerRq.getSize())
                        .setMaxResults(paginationCustomerRq.getSize())
                        .getResultList())
                .countPages((int) Math.ceil(entityManager.createQuery("select count(id) from Customer", Float.class)
                        .getSingleResult() / paginationCustomerRq.getSize()))
                .pageNumber(paginationCustomerRq.getPage())
                .build();
    }
}
