package com.ase.login;

import com.ase.common.EMessageType;
import com.ase.common.RabbitMQMessage;
import com.ase.common.user.User;
import com.ase.login.data.MyUser;
import com.ase.login.network.Converter;
import com.ase.login.rabbitmq.RabbitMQConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class used to publish messages to the defined exchange in {@link RabbitMQConfiguration}
 */
@Component
public class Publisher {

    private final Converter converter;
    private final RabbitTemplate rabbitTemplate;
    @Value("${user.exchange}")
    private String userExchange;

    @Autowired
    public Publisher(RabbitTemplate rabbitTemplate, Converter converter) {
        this.rabbitTemplate = rabbitTemplate;
        this.converter = converter;
    }

    private RabbitMQMessage<User> getUserMessage(MyUser user,
            EMessageType messageType) {
        User networkUser = converter.internalUserToNetworkUser(user);
        return new RabbitMQMessage<>(messageType, networkUser);
    }

    /**
     * Sends a message to the exchange that a new user was created
     *
     * @param user {@link MyUser} the newly created user
     */
    public void newUserCreated(MyUser user) {
        RabbitMQMessage<User> userMessage = getUserMessage(user, EMessageType.NEW);
        rabbitTemplate.convertAndSend(userExchange, "", userMessage);
    }

    /**
     * Sends a message to the exchange that a user was updated
     *
     * @param updatedUser {@link MyUser} the updated user
     */
    public void userUpdated(MyUser updatedUser) {
        RabbitMQMessage<User> userMessage = getUserMessage(updatedUser, EMessageType.UPDATE);
        rabbitTemplate.convertAndSend(userExchange, "", userMessage);
    }

    /**
     * Sends a message to the exchange that a user was deleted
     *
     * @param deletedUser {@link MyUser} the deleted user
     */
    public void userDeleted(MyUser deletedUser) {
        RabbitMQMessage<User> userMessage = getUserMessage(deletedUser, EMessageType.DELETE);
        rabbitTemplate.convertAndSend(userExchange, "", userMessage);
    }
}
