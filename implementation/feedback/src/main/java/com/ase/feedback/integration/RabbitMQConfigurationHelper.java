package com.ase.feedback.integration;

import org.springframework.amqp.core.FanoutExchange;

public class RabbitMQConfigurationHelper {

        public FanoutExchange getExchange(String exchangeName) {
            return new FanoutExchange(exchangeName, true, false);
        }

}
