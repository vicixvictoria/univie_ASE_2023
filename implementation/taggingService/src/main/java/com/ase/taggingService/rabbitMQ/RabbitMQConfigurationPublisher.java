package com.ase.taggingService.rabbitMQ;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfigurationPublisher {

    private final RabbitMQConfigurationHelper<Object> helper = new RabbitMQConfigurationHelper<>();

    @Value("${taggingEvent_exchange}")
    private String exchangeName;

    /**
     * exchange for taggingEvent
     *
     * @return FanoutExchange
     */
    @Bean(name = "taggingEvent_exchange")
    public FanoutExchange exchange() {
        return helper.getExchange(exchangeName);
    }

}