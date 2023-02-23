package ru.ebanievolki.kataplusbot.Handlers;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ebanievolki.kataplusbot.model.Customer;
import ru.ebanievolki.kataplusbot.repository.CustomerRepository;

import java.awt.*;
import java.io.File;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageHandler extends ListenerAdapter {
    private final CustomerRepository customerRepository;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        onStatReceived(event);
        onPlusReceived(event);
        onMinusReceived(event);
    }

    private void onMinusReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();
        if ((event.getChannel() == event.getJDA().getTextChannelsByName("флуд", true).get(0)
                && message.getContentDisplay().equals("-")
                && message.getMessageReference() != null)) {
            User referenceAuthor = message.getMessageReference().getMessage().getAuthor();
            User plusAuthor = message.getAuthor();

            if (referenceAuthor.isBot()) {
                event.getTextChannel().sendMessage(plusAuthor.getAsMention() + " Я бот, мне плевать на минусы.").submit();

            } else if (referenceAuthor.getIdLong() == plusAuthor.getIdLong()) {
                event.getTextChannel().sendMessage(plusAuthor.getAsMention() + " Партия не допустит махинаций с плюсами. За вами выехали.").submit();

            } else {

                if (!customerRepository.isExist(referenceAuthor.getIdLong())) {
                    Customer customer = new Customer(referenceAuthor.getIdLong(), referenceAuthor.getName(), -1L, referenceAuthor.getAsTag());
                    customerRepository.addCustomer(customer);
                } else {
                    Customer customer = customerRepository.getCustomer(referenceAuthor.getIdLong());
                    customer.setPlusCount(customer.getPlusCount() - 1L);
                    customerRepository.updateCustomer(customer);
                }
                Customer customer = customerRepository.getCustomer(referenceAuthor.getIdLong());
                if (customer.getPlusCount() == 39) {
                    event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                            + ", понизил твой рейтинг на -1. Твой рейтинг " + customer.getPlusCount()
                            + ". Партия забирает твою кошкажена в трудовой лагерь.").submit();

                } else if (customer.getPlusCount() == 14) {
                    event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                            + ", понизил твой рейтинг на -1. Твой рейтинг " + customer.getPlusCount()
                            + ". Партия забирает дополнительный миска риса.").submit();

                } else if (customer.getPlusCount() == 0) {
                    event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                            + ", понизил твой рейтинг на -1. Твой рейтинг " + customer.getPlusCount()
                            + ". Партия разочарована в тебе.").submit();

                } else if (customer.getPlusCount() < 0) {
                    event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                            + ", понизил твой рейтинг на -1. Твой рейтинг " + customer.getPlusCount()
                            + ". Ваш рейтинг слишком низок, к вам отправлен комендант для транспортировки в трудовой лагерь.").submit();
                } else {
                    event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                            + ", понизил твой рейтинг на -1. Твой рейтинг " + customer.getPlusCount()
                            + ". Партия следит за вами").submit();
                }
            }
            try {
                Thread.sleep(6);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void onPlusReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();
        if ((event.getChannel() == event.getJDA().getTextChannelsByName("флуд", true).get(0)
                && message.getContentDisplay().equals("+")
                && message.getMessageReference() != null)) {
            User referenceAuthor = message.getMessageReference().getMessage().getAuthor();
            User plusAuthor = message.getAuthor();

            if (referenceAuthor.isBot()) {
                event.getTextChannel().sendMessage(plusAuthor.getAsMention() + " Я бот, мы не гонимся за плюсами.").submit();
            } else if (referenceAuthor.getIdLong() == plusAuthor.getIdLong()) {
                event.getTextChannel().sendMessage(plusAuthor.getAsMention() + " Партия не допустит махинаций с плюсами. За вами выехали.").submit();

            } else {
                if (!customerRepository.isExist(referenceAuthor.getIdLong())) {
                    Customer customer = new Customer(referenceAuthor.getIdLong(), referenceAuthor.getName(), 1L, referenceAuthor.getAsTag());
                    customerRepository.addCustomer(customer);
                } else {
                    Customer customer = customerRepository.getCustomer(referenceAuthor.getIdLong());
                    customer.setPlusCount(customer.getPlusCount() + 1L);
                    customerRepository.updateCustomer(customer);
                }
                Customer customer = customerRepository.getCustomer(referenceAuthor.getIdLong());
                if (customer.getPlusCount() == 40) {
                    event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                                    + ", добавил +1 к твоему социальному рейтингу. Твой рейтинг " + customer.getPlusCount()
                                    + ". Партия выделила тебе одна кошка жена.")
                            .addFile(new File("src/main/resources/img/zhena.jpg")).submit();

                } else if (customer.getPlusCount() == 15) {
                    event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                                    + ", добавил +1 к твоему социальному рейтингу. Твой рейтинг " + customer.getPlusCount()
                                    + ". Партия выделила тебе дополнительный миска риса.")
                            .addFile(new File("src/main/resources/img/ris.jpg")).submit();

                } else {
                    event.getTextChannel().sendMessage(referenceAuthor.getAsMention() + ", пользователь - " + plusAuthor.getName()
                            + ", добавил +1 к твоему социальному рейтингу. Твой рейтинг " + customer.getPlusCount()
                            + ". Партия гордится тобой.").submit();
                }
            }
            try {
                Thread.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public void onStatReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();
        if ((event.getChannel() == event.getJDA().getTextChannelsByName("флуд", true).get(0)
                && message.getContentRaw().equals("/plusStats"))) {
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
            event.getChannel().sendMessageEmbeds(embedBuilder.build()).submit();
            try {
                Thread.sleep(6);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
