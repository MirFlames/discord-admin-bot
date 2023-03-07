package ru.ebanievolki.kataplusbot.Handlers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.ebanievolki.kataplusbot.Handlers.annotations.CommandController;
import ru.ebanievolki.kataplusbot.Handlers.annotations.CommandMapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@RequiredArgsConstructor
public class MessageHandler extends ListenerAdapter {

    private final ApplicationContext applicationContext;

    @Value("${messages.buffer-size}")
    private int MESSAGES_BUFFER_SIZE;

    private static final ConcurrentLinkedQueue<RequestResponse> messages =
            new ConcurrentLinkedQueue<>();

    private record RequestResponse(Message userMessage, Message botAnswer) {}

    @SneakyThrows
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        for (Object o : applicationContext.getBeansWithAnnotation(CommandController.class).values()) {
            for (Method method : o.getClass().getMethods()) {
                if (method.isAnnotationPresent(CommandMapping.class) &&
                        Arrays.asList(method.getAnnotation(CommandMapping.class).value())
                        .contains(event.getMessage().getContentDisplay())) {
                    messages.add(new RequestResponse(event.getMessage(),
                            (Message) method.invoke(o, event)));
                }
            }
        }
        if (messages.size() > MESSAGES_BUFFER_SIZE) {
            RequestResponse requestResponse = messages.remove();
            requestResponse.botAnswer.delete().submit();
            requestResponse.userMessage.delete().submit();
        }
    }
}