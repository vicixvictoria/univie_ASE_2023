package com.ase.notification.integration.rabbitMQConfiguration;

import com.ase.notification.integration.Subscriber;
import com.ase.notification.integration.rabbitMQConfiguration.helper.IRabbitMQConfiguration;
import com.ase.notification.integration.rabbitMQConfiguration.helper.RabbitMQConfigurationHelper;
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
public class RabbitMQBookmarkedConfiguration implements IRabbitMQConfiguration {

    private final RabbitMQConfigurationHelper configurationHelper = new RabbitMQConfigurationHelper();

    @Value("${event.bookmarked.exchange}")
    private String exchangeName;

    @Value("${notification.bookmarked.queue.name}")
    private String queueName;

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "bookmarked-queue")
    public Queue queue() {
        return configurationHelper.getQueue(queueName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "bookmarked-exchange")
    public FanoutExchange exchange() {
        return configurationHelper.getExchange(exchangeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "bookmarked-binding")
    public Binding binding(@Qualifier("bookmarked-queue") Queue queue,
            @Qualifier("bookmarked-exchange") FanoutExchange exchange) {
        return configurationHelper.getBinding(queue, exchange);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "bookmarked-callback")
    public MessageListenerAdapter messageListenerAdapter(Subscriber subscriber) {
        return configurationHelper.getListenerAdapter(subscriber, "registerNotificationBookmarked");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "bookmarked-callback-binding")
    public SimpleMessageListenerContainer eventListenerContainer(
            ConnectionFactory connectionFactory,
            @Qualifier("bookmarked-callback") MessageListenerAdapter adapter) {
        return configurationHelper.getEventListenerContainer(connectionFactory, adapter, queueName);
    }
}
