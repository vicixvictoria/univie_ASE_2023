package com.ase.recommender.integration;

import com.ase.recommender.integration.RabbitMQConfigurationHelper;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ to send notifications.
 */
@Configuration
public class RabbitMQSendNotificationConfiguration {

    @Value("${sendnotification.exchange}")
    private String exchangeName;
    private final RabbitMQConfigurationHelper helper = new RabbitMQConfigurationHelper();

    /**
     * Bean definition for the fanout exchange used to send notifications.
     *
     * @return The configured FanoutExchange object.
     */
    @Bean("sendnotification-exchange")
    public FanoutExchange exchange() {
        return helper.getExchange(exchangeName);
    }
}
