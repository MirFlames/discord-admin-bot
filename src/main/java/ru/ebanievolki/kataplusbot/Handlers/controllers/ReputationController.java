package ru.ebanievolki.kataplusbot.Handlers.controllers;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.ebanievolki.kataplusbot.Handlers.annotations.CommandController;
import ru.ebanievolki.kataplusbot.Handlers.annotations.CommandMapping;
import ru.ebanievolki.kataplusbot.service.abstracts.CustomerService;

import javax.validation.constraints.NotNull;
import java.util.concurrent.ExecutionException;

@CommandController
@RequiredArgsConstructor
public class ReputationController {
    private final CustomerService customerService;

    @CommandMapping(value = "-", description = "Ответь минусом на сообщение пользователя чтобы понизить его репутацию", referenceRequired = true)
    public Message onMinusReceived(@NotNull MessageReceivedEvent event) throws ExecutionException, InterruptedException {
        return event
                .getTextChannel()
                .sendMessage(customerService.downReputation(event.getMessage().getAuthor(),
                        event.getMessage().getMessageReference().getMessage().getAuthor()))
                .submit().get();
    }

    @CommandMapping(value = "+", description = "Ответь плюсом на сообщение пользователя чтобы повысить его репутацию", referenceRequired = true)
    public Message onPlusReceived(@NotNull MessageReceivedEvent event) throws ExecutionException, InterruptedException {
        return event
                .getTextChannel()
                .sendMessage(customerService.upReputation(event.getMessage().getAuthor(),
                        event.getMessage().getMessageReference().getMessage().getAuthor()))
                .submit().get();
    }

    @CommandMapping(value = {"/plusStats", "/stats"}, description = "Вывести статистику")
    public Message onStatReceived(@NotNull MessageReceivedEvent event) throws ExecutionException, InterruptedException {
        return event
                .getChannel()
                .sendMessageEmbeds(customerService.getCustomersStats())
                .submit().get();
    }
}
