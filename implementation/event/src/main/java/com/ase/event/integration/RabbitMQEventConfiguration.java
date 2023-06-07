package com.ase.event.integration;

import com.ase.event.integration.helper.RabbitMQConfigurationHelper;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQEventConfiguration {

    private final RabbitMQConfigurationHelper<Object> helper = new RabbitMQConfigurationHelper<>();

    @Value("${event.exchange}")
    private String exchangeName;

    @Bean(name = "event-exchange")
    public FanoutExchange exchange() {
        return helper.getExchange(exchangeName);}


}
