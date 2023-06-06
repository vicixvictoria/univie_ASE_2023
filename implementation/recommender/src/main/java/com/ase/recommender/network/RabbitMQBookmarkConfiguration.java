package com.ase.recommender.network;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The RabbitMQBookmarkConfiguration class configures the RabbitMQ bookmark listeners.
 */
@Configuration
public class RabbitMQBookmarkConfiguration {

    private final RabbitMQConfigurationHelper configurationHelper = new RabbitMQConfigurationHelper();

    @Value("${bookmark.exchange}")
    private String exchangeName;

    @Value("${bookmark.queue}")
    private String queueName;

    /**
     * Creates a queue for bookmark messages.
     *
     * @return The queue for bookmark messages.
     */
    @Bean(name = "bookmarked-queue")
    public Queue queue() {
        return configurationHelper.getQueue(queueName);
    }

    /**
     * Creates a fanout exchange for bookmark messages.
     *
     * @return The fanout exchange for bookmark messages.
     */
    @Bean(name = "bookmarked-exchange")
    public FanoutExchange exchange() {
        return configurationHelper.getExchange(exchangeName);
    }

    /**
     * Creates a binding between the bookmark queue and exchange.
     *
     * @param queue    The bookmark queue.
     * @param exchange The bookmark exchange.
     * @return The binding between the bookmark queue and exchange.
     */
    @Bean(name = "bookmarked-binding")
    public Binding binding(@Qualifier("bookmarked-queue") Queue queue,
            @Qualifier("bookmarked-exchange") FanoutExchange exchange) {
        return configurationHelper.getBinding(queue, exchange);
    }

    /**
     * Creates a message listener adapter for bookmark messages.
     *
     * @param subscriber The subscriber for bookmark messages.
     * @return The message listener adapter for bookmark messages.
     */
    @Bean(name = "bookmarked-callback")
    public MessageListenerAdapter messageListenerAdapter(Subscriber subscriber) {
        return configurationHelper.getListenerAdapter(subscriber, "registerNotificationBookmarked");
    }
}
