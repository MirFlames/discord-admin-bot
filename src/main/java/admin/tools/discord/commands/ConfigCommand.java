package admin.tools.discord.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import reactor.core.publisher.Mono;

public class ConfigCommand implements SlashCommand {
    @Override
    public String getName() {
        return "config";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        return event.reply().withEphemeral(true).withContent("Пока пусто");
    }
}
