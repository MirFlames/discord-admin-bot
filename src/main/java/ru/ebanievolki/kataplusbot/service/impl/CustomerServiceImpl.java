package ru.ebanievolki.kataplusbot.service.impl;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ebanievolki.kataplusbot.model.Customer;
import ru.ebanievolki.kataplusbot.model.PaginationCustomerRq;
import ru.ebanievolki.kataplusbot.model.PaginationDTO;
import ru.ebanievolki.kataplusbot.repository.CustomerRepository;
import ru.ebanievolki.kataplusbot.service.abstracts.CustomerService;

import java.awt.*;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public PaginationDTO<Customer> getAllWithPaging(PaginationCustomerRq paginationCustomerRq) {
        return customerRepository.getAllWithPaging(paginationCustomerRq);
    }

    @Override
    @Transactional
    public String upReputation(User from, User to) {
        if (to.isBot()) {
            return from.getAsMention() + " Я бот, мы не гонимся за плюсами.";

        } else if (to.getIdLong() == from.getIdLong()) {
            return from.getAsMention() + " Партия не допустит махинаций с плюсами. За вами выехали.";

        }
        if (!customerRepository.isExist(to.getIdLong())) {
            Customer customer = new Customer(to.getIdLong(), to.getName(), 1L, to.getAsTag());
            customerRepository.addCustomer(customer);
        } else {
            Customer customer = customerRepository.getCustomer(to.getIdLong());
            customer.setPlusCount(customer.getPlusCount() + 1L);
            customerRepository.updateCustomer(customer);
        }

        Customer customer = customerRepository.getCustomer(to.getIdLong());
        return to.getAsMention() + ", пользователь - " + from.getName()
                + ", добавил +1 к твоему социальному рейтингу. Твой рейтинг " + customer.getPlusCount();
    }

    @Override
    @Transactional
    public String downReputation(User from, User to) {
        if (to.isBot()) {
            return from.getAsMention() + " Я бот, мне плевать на минусы.";

        } else if (to.getIdLong() == from.getIdLong()) {
            return from.getAsMention() + " Партия не допустит махинаций с минусами. За вами выехали.";
        }

        if (!customerRepository.isExist(to.getIdLong())) {
            Customer customer = new Customer(to.getIdLong(), to.getName(), -1L, to.getAsTag());
            customerRepository.addCustomer(customer);
        } else {
            Customer customer = customerRepository.getCustomer(to.getIdLong());
            customer.setPlusCount(customer.getPlusCount() - 1L);
            customerRepository.updateCustomer(customer);
        }

        Customer customer = customerRepository.getCustomer(to.getIdLong());
        return to.getAsMention() + ", пользователь - " + from.getName()
                + ", понизил твой рейтинг на -1. Твой рейтинг " + customer.getPlusCount();
    }

    @Override
    @Transactional
    public MessageEmbed getCustomersStats() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        List<Customer> customers = customerRepository.getStats();
        embedBuilder.setTitle("Доска почета партии");
        embedBuilder.setColor(Color.lightGray);
        embedBuilder.setImage("https://medialeaks.ru/wp-content/uploads/2020/05/94d-369x500.jpg");
        for (Customer customer : customers) {
            embedBuilder.addField("", customer.getName(), true);
            embedBuilder.addField("Social Rate", String.valueOf(customer.getPlusCount()), true);
            embedBuilder.addBlankField(true);
        }
        return embedBuilder.build();
    }
}
