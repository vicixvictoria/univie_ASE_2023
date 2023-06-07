package com.ase.recommender.integration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The RabbitMQEventConfiguration class configures RabbitMQ for handling events.
 */
@Configuration
public class RabbitMQEventConfiguration  {

    private final RabbitMQConfigurationHelper configurationHelper = new RabbitMQConfigurationHelper();
    @Value("${event.exchange}")
    private String exchangeName;

    @Value("${event.queue}")
    private String queueName;

    /**
     * Creates a queue for handling events.
     *
     * @return the created queue.
     */
    @Bean(name = "event-queue")
    public Queue queue() {
        return configurationHelper.getQueue(queueName);
    }

    /**
     * Creates a fanout exchange for broadcasting events.
     *
     * @return the created fanout exchange.
     */
    @Bean(name = "event-exchange")
    public FanoutExchange exchange() {
        return configurationHelper.getExchange(exchangeName);
    }

    /**
     * Binds the queue to the fanout exchange.
     *
     * @param queue the queue to be bound.
     * @param exchange the fanout exchange to bind the queue to.
     * @return the created binding.
     */
    @Bean(name = "event-binding")
    public Binding binding(@Qualifier("event-queue") Queue queue,
            @Qualifier("event-exchange") FanoutExchange exchange) {
        return configurationHelper.getBinding(queue, exchange);
    }

    /**
     * Creates a message listener adapter for handling event messages.
     *
     * @param subscriber the Subscriber to be used by the message listener adapter.
     * @return the created message listener adapter.
     */
    @Bean(name = "event-callback")
    public MessageListenerAdapter messageListenerAdapter(Subscriber subscriber) {
        return configurationHelper.getListenerAdapter(subscriber, "eventConsumer");
    }
}
