package com.ase.feedback.integration;

import com.ase.common.EMessageType;
import com.ase.common.RabbitMQMessage;
import com.ase.common.feedback.Feedback;
import com.ase.feedback.domain.MyFeedback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
    @Value("${feedback.exchange}")
    private String feedbackExchange;
    private final RabbitTemplate rabbitTemplate;
    private final Converter converter = new Converter();



    @Autowired
    public Publisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * sends a myFeedback with a specified action
     * @param action EMessageType
     * @param myFeedback MyFeedback
     */
    public void sendFeedback(EMessageType action, MyFeedback myFeedback) {
        RabbitMQMessage<Feedback> message = new RabbitMQMessage<>(action,
                converter.feedbackToNetworkFeedback(myFeedback));
        rabbitTemplate.convertAndSend(feedbackExchange, "", message);

    }
    /**
     * sends a message to the exchange that a new MyFeedback was created
     *
     * @param myFeedback the newly created user
     */
    public void newFeedbackCreated(MyFeedback myFeedback) {
        sendFeedback(EMessageType.NEW, myFeedback);
    }

    /**
     * sends a message to the exchange that a MyFeedback was updated
     *
     * @param myFeedback the updated myFeedback
     */
    public void feedbackUpdated(MyFeedback myFeedback) {
        sendFeedback(EMessageType.UPDATE, myFeedback);
    }
}