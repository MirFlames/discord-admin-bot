package ru.ebanievolki.kataplusbot.repository;

import javax.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ebanievolki.kataplusbot.model.Customer;
import ru.ebanievolki.kataplusbot.model.PaginationCustomerRq;
import ru.ebanievolki.kataplusbot.model.PaginationDTO;

import java.util.List;


@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getStats() {
        return entityManager.createQuery("from Customer order by plusCount desc", Customer.class)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    @Transactional
    public void addCustomer(Customer customer) {
        entityManager.persist(customer);
    }

    @Override
    @Transactional
    public void updateCustomer(Customer customer) {
        entityManager.merge(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExist(Long discordId) {
        return !entityManager.createQuery("from Customer where id = :discordId", Customer.class)
                .setParameter("discordId", discordId)
                .getResultList().isEmpty();
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomer(Long discordId) {
        return entityManager.createQuery("from Customer where id = :discordId", Customer.class)
                .setParameter("discordId", discordId)
                .getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
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
