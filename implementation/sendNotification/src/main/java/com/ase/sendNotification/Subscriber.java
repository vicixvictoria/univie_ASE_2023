package com.ase.sendNotification;

import com.ase.common.RabbitMQMessage;
import com.ase.common.sendNotification.NotificationContent;
import com.ase.common.user.User;
import com.ase.sendNotification.model.NotificationUser;
import com.ase.sendNotification.network.Converter;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class defining callback methods, which will be called when a RabbitMQ message arrives. Can be
 * seen as a parallel to {@link org.springframework.web.bind.annotation.RestController}
 */
@Component
public class Subscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    public final NotificationSendService notificationSendService;

    @Autowired
    public Subscriber(NotificationSendService notificationSendService) {
        this.notificationSendService = notificationSendService;
    }

    /**
     * Extracts the relevant information from {@link NotificationContent} and forwards in order to
     * send a notification.
     *
     * @param notificationContent {@link NotificationContent} content of the to be sent
     *                            notification
     */
    public void sendNotification(NotificationContent notificationContent) {
        LOGGER.info(String.format("Sending a message to %s", notificationContent.userId()));
        notificationSendService.sendNotification(notificationContent.userId(),
                notificationContent.message());
    }

    public void userConsumer(RabbitMQMessage<User> userMessage) {
        Converter converter = new Converter();
        NotificationUser user = converter.toInternalUser(userMessage.getContent());
        LOGGER.info(String.format("Received a user with id %s", user.getId()));
        switch (userMessage.getMessageType()) {
            case NEW -> notificationSendService.newUser(user);
            case UPDATE -> notificationSendService.updateUser(user);
            case DELETE -> notificationSendService.deleteUser(user);
        }
    }
}
