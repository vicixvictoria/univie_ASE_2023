package com.ase.taggingService;

import com.ase.common.EMessageType;
import com.ase.common.RabbitMQMessage;
import com.ase.common.taggingEvent.ETags;
import com.ase.common.taggingEvent.TaggingEventMessage;
import java.util.List;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${taggingEvent_exchange}")
    private String exchangeName;

    @Autowired
    public Publisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * publishes if new bookmarked event is created
     */
    public void newTaggingEvent(String eventId, String userId, List<ETags> eventTag) {
        TaggingEventMessage taggingMessage = new TaggingEventMessage(userId, eventId, eventTag);
        RabbitMQMessage<TaggingEventMessage> taggingMessageRabbitMQMessage = new RabbitMQMessage<>(
                EMessageType.UPDATE, taggingMessage);
        rabbitTemplate.convertAndSend("", taggingMessageRabbitMQMessage);
    }


    /**
     * publishes if taggingEvent is deleted
     */
    public void deleteTaggingEvent(String eventId, String userId) {
        TaggingEventMessage taggingMessage = new TaggingEventMessage(userId, eventId, null);
        RabbitMQMessage<TaggingEventMessage> taggingMessageRabbitMQMessage = new RabbitMQMessage<>(
                EMessageType.DELETE, taggingMessage);
        rabbitTemplate.convertAndSend(exchangeName, "", taggingMessageRabbitMQMessage);
    }

    /**
     * publishes if taggingEvent is updated/new tag is added
     */
    public void updateTaggingEvent(String eventId, String userId, List<ETags> eventTag) {
        TaggingEventMessage taggingMessage = new TaggingEventMessage(userId, eventId, eventTag);
        RabbitMQMessage<TaggingEventMessage> taggingMessageRabbitMQMessage = new RabbitMQMessage<>(
                EMessageType.UPDATE, taggingMessage);
        rabbitTemplate.convertAndSend(exchangeName, "", taggingMessageRabbitMQMessage);
    }
}