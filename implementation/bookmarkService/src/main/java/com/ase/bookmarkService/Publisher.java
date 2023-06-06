package com.ase.bookmarkService;

import com.ase.common.EMessageType;
import com.ase.common.RabbitMQMessage;
import com.ase.common.bookmarkEvent.BookmarkEventMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    @Value("${bookmarkEvent_exchange}")
    private String exchangeName;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public Publisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * publishes if new bookmarked event is created
     */
    public void newBookmarkEvent(String eventId, String userId) {
        BookmarkEventMessage bookmarkMessage = new BookmarkEventMessage(userId, eventId);
        RabbitMQMessage<BookmarkEventMessage> bookmarkMessageRabbitMQMessage = new RabbitMQMessage<>(
                EMessageType.UPDATE, bookmarkMessage);
        rabbitTemplate.convertAndSend(exchangeName, "", bookmarkMessageRabbitMQMessage);
    }


    /**
     * publishes if bookmarkEvent is deleted
     */
    public void deleteBookmarkEvent(String eventId, String userId) {
        BookmarkEventMessage bookmarkMessage = new BookmarkEventMessage(userId, eventId);
        RabbitMQMessage<BookmarkEventMessage> bookmarkMessageRabbitMQMessage = new RabbitMQMessage<>(
                EMessageType.DELETE, bookmarkMessage);
        rabbitTemplate.convertAndSend(exchangeName, "", bookmarkMessageRabbitMQMessage);
    }
}
