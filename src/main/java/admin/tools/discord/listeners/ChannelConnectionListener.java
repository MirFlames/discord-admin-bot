package admin.tools.discord.listeners;

import discord4j.core.event.domain.VoiceStateUpdateEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ChannelConnectionListener implements EventListener<VoiceStateUpdateEvent>{
    @Override
    public Class<VoiceStateUpdateEvent> getEventType() {
        return VoiceStateUpdateEvent.class;
    }

    @Override
    public Mono<Void> execute(VoiceStateUpdateEvent event) {
        return Mono.just(event).filter(e -> (e.isJoinEvent() || e.isMoveEvent()) &&
                e.getCurrent().getChannel().block().getName().equals("Создать канал")).then();
    }
}
