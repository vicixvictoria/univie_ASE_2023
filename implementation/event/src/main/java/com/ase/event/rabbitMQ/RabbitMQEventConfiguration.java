package com.ase.event.rabbitMQ;

import com.ase.event.rabbitMQ.helper.RabbitMQConfigurationHelper;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class RabbitMQEventConfiguration {

    private final RabbitMQConfigurationHelper<Object> helper = new RabbitMQConfigurationHelper<>();

    @Value("${event.exchange}")
    private String exchangeName;

    @Bean(name = "event-exchange")
    public FanoutExchange exchange() {
        return helper.getExchange(exchangeName);}


}
