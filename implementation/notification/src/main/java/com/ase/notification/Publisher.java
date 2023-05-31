package com.ase.notification;

import com.ase.common.EMessageType;
import com.ase.common.RabbitMQMessage;
import com.ase.common.sendNotification.NotificationContent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    @Value("${sendNotification.exchange}")
    private String sendNotificationExchangeName;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public Publisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    /**
     * Publishes a notification to the configured RabbitMQ exchange.
     *
     * @param userId  the id of the user who will receive a notification
     * @param message the message of the notification
     */
    public void publishNotification(String userId, String message) {
        NotificationContent notificationContent = new NotificationContent(userId, message);
        RabbitMQMessage<NotificationContent> networkMessage =
                new RabbitMQMessage<>(EMessageType.NEW, notificationContent);
        rabbitTemplate.convertAndSend(sendNotificationExchangeName, "", networkMessage);
    }

}
