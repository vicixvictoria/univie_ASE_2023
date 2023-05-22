package com.ase.event;

import com.ase.common.EMessageType;
import com.ase.common.RabbitMQMessage;
import com.ase.event.network.Converter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    @Value("${event.exchange}")
    private final String exchangeName;

    private final Converter converter = new Converter();
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public Publisher(RabbitTemplate template) {
        this.rabbitTemplate = template;
    }

    private void sendEvent(Event event, EMessageType messageType) {
        var networkEvent = converter.getNetworkEvent(event);
        RabbitMQMessage<com.ase.common.event.Event> message = new RabbitMQMessage<>(messageType,
                networkEvent);
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }

    public void newEvent(Event event) {
        sendEvent(event, EMessageType.NEW);
    }

    public void updateEvent(Event event) {
        sendEvent(event, EMessageType.UPDATE);
    }

    public void deleteEvent(Event event) {
        sendEvent(event, EMessageType.DELETE);
    }

}
