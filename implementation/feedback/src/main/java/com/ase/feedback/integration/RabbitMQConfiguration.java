package com.ase.feedback.integration;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${feedback.exchange}")
    private String feedbackExchange;

    private final RabbitMQConfigurationHelper helper = new RabbitMQConfigurationHelper();

    @Bean(name = "feedback.exchange")
    public FanoutExchange feedbackExchange() {
        return helper.getExchange(feedbackExchange);
    }

}