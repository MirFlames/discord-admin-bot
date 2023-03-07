package ru.ebanievolki.kataplusbot.Handlers.controllers;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.ebanievolki.kataplusbot.Handlers.annotations.CommandController;
import ru.ebanievolki.kataplusbot.Handlers.annotations.CommandMapping;
import ru.ebanievolki.kataplusbot.service.abstracts.ChatBotConfigService;

import javax.validation.constraints.NotNull;
import java.util.concurrent.ExecutionException;

@CommandController
@RequiredArgsConstructor
public class BotInteractionController {
    private final ChatBotConfigService chatBotConfigService;

    @CommandMapping(value = "/settings", description = "Панель настроек")
    public Message onSettingsReceived(@NotNull MessageReceivedEvent event) throws ExecutionException, InterruptedException {
        return event
                .getChannel()
                .sendMessageEmbeds(chatBotConfigService.getState())
                .submit().get();
    }

    @CommandMapping(value = "test", description = "хз")
    public Message testMethod(@NotNull MessageReceivedEvent event) throws ExecutionException, InterruptedException {
        return event
                .getChannel()
                .sendMessage(event.getMessage()).submit().get();
    }
}
