package com.ase.login;

import com.ase.login.data.MyUser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class used to publish messages to the defined exchange in {@link RabbitMQConfiguration}
 */
@Component
public class Publisher {
    @Value("${user.exchange}")
    private String userExchange;
    private  final RabbitTemplate rabbitTemplate;

    @Autowired
    public Publisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Sends a message to the exchange that a new user was created
     * @param user {@link MyUser} the newly created user
     */
    public void newUserCreated(MyUser user) {
        rabbitTemplate.convertAndSend(userExchange, "", user);
    }

    /**
     * Sends a message to the exchange that a user was updated
     * @param updatedUser {@link MyUser} the updated user
     */
    public void userUpdated(MyUser updatedUser) {
        rabbitTemplate.convertAndSend(userExchange, "", updatedUser);
    }
}
