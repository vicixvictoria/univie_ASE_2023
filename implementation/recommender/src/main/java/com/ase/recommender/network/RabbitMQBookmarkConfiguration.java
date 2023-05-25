package com.ase.recommender.network;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQBookmarkConfiguration {

    private final RabbitMQConfigurationHelper configurationHelper = new RabbitMQConfigurationHelper();

    @Value("${bookmark.exchange}")
    private String exchangeName;

    @Value("${bookmark.queue}")
    private String queueName;

    @Bean(name = "bookmarked-queue")
    public Queue queue() {
        return configurationHelper.getQueue(queueName);
    }

    @Bean(name = "bookmarked-exchange")
    public FanoutExchange exchange() {
        return configurationHelper.getExchange(exchangeName);
    }

    @Bean(name = "bookmarked-binding")
    public Binding binding(@Qualifier("bookmarked-queue") Queue queue,
            @Qualifier("bookmarked-exchange") FanoutExchange exchange) {
        return configurationHelper.getBinding(queue, exchange);
    }

    @Bean(name = "bookmarked-callback")
    public MessageListenerAdapter messageListenerAdapter(Subscriber subscriber) {
        return configurationHelper.getListenerAdapter(subscriber, "registerNotificationBookmarked");
    }
}
