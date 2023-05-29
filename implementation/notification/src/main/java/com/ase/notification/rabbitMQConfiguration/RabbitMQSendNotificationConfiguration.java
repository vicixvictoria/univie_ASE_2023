package com.ase.notification.rabbitMQConfiguration;

import com.ase.notification.rabbitMQConfiguration.helper.RabbitMQConfigurationHelper;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQSendNotificationConfiguration {

    @Value("${sendNotification.exchange}")
    private String exchangeName;

    private final RabbitMQConfigurationHelper helper = new RabbitMQConfigurationHelper();

    @Bean("sendnotification-exchange")
    public FanoutExchange exchange() {
        return helper.getExchange(exchangeName);
    }

}
