package com.ase.bookmarkService.rabbitMQ;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfigurationPublisher {

    private final RabbitMQConfigurationHelper<Object> helper = new RabbitMQConfigurationHelper<>();

    @Value("${bookmarkEvent_exchange}")
    private String exchangeName;

    /**
     * exchange for bookmarkEvent
     *
     * @return FanoutExchange
     */
    @Bean(name = "bookmarkEvent_exchange")
    public FanoutExchange exchange() {
        return helper.getExchange(exchangeName);
    }

}
