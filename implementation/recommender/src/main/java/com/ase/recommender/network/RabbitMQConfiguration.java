package com.ase.recommender.network;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${recommender.exchange}")
    private String attendanceExchange;
    private final RabbitMQConfigurationHelper configHelper = new RabbitMQConfigurationHelper();

    @Bean(name = "recommender.exchange")
    public FanoutExchange attendanceExchange() {
        return configHelper.getExchange(attendanceExchange);
    }
}

