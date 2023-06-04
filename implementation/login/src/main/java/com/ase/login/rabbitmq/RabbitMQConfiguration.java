package com.ase.login.rabbitmq;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class, responsible for setting up RabbitMQ. It sets up:
 * <ul>
 *     <li>The exchange {@code user.exchange} to which new users will be published</li>
 * </ul>
 * The exchange name is defined {@code application.name}
 */
@Configuration
public class RabbitMQConfiguration {

    @Value("${user.exchange}")
    private String userExchange;

    @Bean
    public FanoutExchange userExchange() {
        return new FanoutExchange(userExchange, true, false);
    }

}
