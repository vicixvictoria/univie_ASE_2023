package com.ase.feedback.network;

import com.ase.common.EMessageType;
import com.ase.common.RabbitMQMessage;
import com.ase.common.feedback.FeedbackMessage;
import com.ase.feedback.model.data.Feedback;
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
     * sends a feedback with a specified action
     * @param action EMessageType
     * @param feedback Feedback
     */
    public void sendFeedback(EMessageType action, Feedback feedback) {
        RabbitMQMessage<FeedbackMessage> message = new RabbitMQMessage<>(action,
                converter.getNetworkFeedback(feedback));
        rabbitTemplate.convertAndSend(feedbackExchange, "", message);

    }
    /**
     * sends a message to the exchange that a new Feedback was created
     *
     * @param feedback the newly created user
     */
    public void newFeedbackCreated(Feedback feedback) {
        sendFeedback(EMessageType.NEW, feedback);
    }

    /**
     * sends a message to the exchange that a Feedback was updated
     *
     * @param feedback the updated feedback
     */
    public void feedbackUpdated(Feedback feedback) {
        sendFeedback(EMessageType.UPDATE, feedback);
    }
}