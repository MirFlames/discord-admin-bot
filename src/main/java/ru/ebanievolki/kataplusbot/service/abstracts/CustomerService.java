package ru.ebanievolki.kataplusbot.service.abstracts;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import ru.ebanievolki.kataplusbot.model.Customer;
import ru.ebanievolki.kataplusbot.model.PaginationCustomerRq;
import ru.ebanievolki.kataplusbot.model.PaginationDTO;

import java.util.List;

public interface CustomerService {

    PaginationDTO<Customer> getAllWithPaging(PaginationCustomerRq paginationCustomerRq);
    String upReputation(User from, User to);
    String downReputation(User from, User to);
    MessageEmbed getCustomersStats();
}
