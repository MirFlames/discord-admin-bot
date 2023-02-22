package ru.ebanievolki.kataplusbot.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ebanievolki.kataplusbot.model.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Transactional
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Customer> getStats() {
        return entityManager.createQuery("from Customer order by plusCount", Customer.class)
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
    public boolean isExist(Long discordId) {
        String jpql = "from Customer where discordId = :discordId";
        TypedQuery<Customer> query = entityManager.createQuery(jpql, Customer.class).setParameter("discordId", discordId);
        return query.getResultList().size() > 0;
    }

    @Override
    public Customer getCustomer(Long discordId) {
        String jpql = "from Customer where discordId = :discordId";
        TypedQuery<Customer> query = entityManager.createQuery(jpql, Customer.class).setParameter("discordId", discordId);
        return query.getSingleResult();
    }
}
