package com.ase.event;

import com.ase.common.RabbitMQMessage;
import com.ase.event.domain.EEventTypes;
import com.ase.event.domain.Event;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

class EventPublisherTest {

    @Test
    public void testNewEvent() {
        // Create a mock event
        Event event = new Event("abc", EEventTypes.HEALTH,345,new Date(),"testing","abcdef","Test1");

        // Mock the rabbitTemplate and set expectations
        RabbitTemplate rabbitTemplate = Mockito.mock(RabbitTemplate.class);
        Publisher publisher = new Publisher(rabbitTemplate);

        // Call the method under test
        publisher.newEvent(event);

        // Verify that the rabbitTemplate's convertAndSend method was called with the expected parameters
        Mockito.verify(rabbitTemplate).convertAndSend(Mockito.eq(publisher.exchangeName), Mockito.eq(""), Mockito.any(
                RabbitMQMessage.class));
    }


    @Test
    public void testUpdateEvent() {
        // Create a mock event
        Event event = new Event("abc", EEventTypes.HEALTH,345,new Date(),"testing","abcdef","Test1");

        // Mock the rabbitTemplate and set expectations
        RabbitTemplate rabbitTemplate = Mockito.mock(RabbitTemplate.class);
        Publisher publisher = new Publisher(rabbitTemplate);

        // Call the method under test
        publisher.updateEvent(event);

        // Verify that the rabbitTemplate's convertAndSend method was called with the expected parameters
        Mockito.verify(rabbitTemplate).convertAndSend(Mockito.eq(publisher.exchangeName), Mockito.eq(""), Mockito.any(RabbitMQMessage.class));
    }

    @Test
    public void testDeleteEvent() {
        // Create a mock event
        Event event = new Event("abc", EEventTypes.HEALTH,345,new Date(),"testing","abcdef","Test1");

        // Mock the rabbitTemplate and set expectations
        RabbitTemplate rabbitTemplate = Mockito.mock(RabbitTemplate.class);
        Publisher publisher = new Publisher(rabbitTemplate);

        // Call the method under test
        publisher.deleteEvent(event);

        // Verify that the rabbitTemplate's convertAndSend method was called with the expected parameters
        Mockito.verify(rabbitTemplate).convertAndSend(Mockito.eq(publisher.exchangeName), Mockito.eq(""), Mockito.any(RabbitMQMessage.class));
    }


}
