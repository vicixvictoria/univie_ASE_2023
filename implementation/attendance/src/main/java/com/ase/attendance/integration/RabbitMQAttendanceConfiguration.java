package com.ase.attendance.integration;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The RabbitMQEventConfiguration class configures RabbitMQ for handling attendance messages.
 */
@Configuration
public class RabbitMQAttendanceConfiguration  {

    /**
     * Manages RabbitMQ configuration for attendance module.
     */
    private final RabbitMQConfigurationHelper configurationHelper = new RabbitMQConfigurationHelper();

    /**
     * The name of the exchange used for attendance.
     */
    @Value("${attendance.exchange}")
    private String exchangeName;

    /**
     * Helper instance for RabbitMQ configuration.
     */
    private final RabbitMQConfigurationHelper helper = new RabbitMQConfigurationHelper();

    /**
     * Creates the attendance exchange bean.
     *
     * @return The fanout exchange for attendance.
     */
    @Bean("attendance-exchange")
    public FanoutExchange exchange() {
        return helper.getExchange(exchangeName);
    }
}
