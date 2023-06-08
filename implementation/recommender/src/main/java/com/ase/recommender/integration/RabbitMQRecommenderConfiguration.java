package com.ase.recommender.integration;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQRecommenderConfiguration {

    @Value("${recommender.exchange}")
    private String exchangeName;
    private final RabbitMQConfigurationHelper helper = new RabbitMQConfigurationHelper();

    /**
     * Creates a bean for the RabbitMQ fanout exchange.
     *
     * @return The fanout exchange for communication.
     */
    @Bean("recommender-exchange")
    public FanoutExchange exchange() {
        return helper.getExchange(exchangeName);
    }

}
