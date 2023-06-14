package com.ase.event;

import com.ase.common.EMessageType;
import com.ase.common.RabbitMQMessage;
import com.ase.event.domain.Event;
import com.ase.event.integration.network.Converter;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    @Value("${event.exchange}")
    String exchangeName;

    private final Converter converter = new Converter();
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public Publisher(RabbitTemplate template) {
        this.rabbitTemplate = template; }

    private void sendEvent(Event event, EMessageType messageType) {
        var networkEvent = converter.getNetworkEvent(event);
        RabbitMQMessage<com.ase.common.event.Event> message = new RabbitMQMessage<>(messageType,
                networkEvent);
        rabbitTemplate.convertAndSend(exchangeName, "", message);
        LOGGER.info("Publisher sent event");
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
