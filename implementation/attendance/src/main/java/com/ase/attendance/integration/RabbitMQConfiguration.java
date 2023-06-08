package com.ase.attendance.integration;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The RabbitMQConfiguration class is responsible for setting up the necessary RabbitMQ configuration for attendance exchange.
 */
@Configuration
public class RabbitMQConfiguration {

    @Value("${attendance.exchange}")
    private String attendanceExchange;
    private final RabbitMQConfigurationHelper configHelper = new RabbitMQConfigurationHelper();

    /**
     * Creates a fanout exchange for attendance using a helper.
     *
     * @return the created fanout exchange.
     */
    @Bean(name = "attendance.exchange")
    public FanoutExchange attendanceExchange() {
        return configHelper.getExchange(attendanceExchange);
    }
}

