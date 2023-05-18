package com.ase.notification.rabbitMQConfiguration;

import com.ase.notification.Subscriber;
import com.ase.notification.rabbitMQConfiguration.helper.IRabbitMQConfiguration;
import com.ase.notification.rabbitMQConfiguration.helper.RabbitMQConfigurationHelper;
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
public class RabbitMQRegisteredConfiguration implements IRabbitMQConfiguration {

    private final RabbitMQConfigurationHelper configurationHelper = new RabbitMQConfigurationHelper();

    @Value("${event.registered.exchange}")
    private String exchangeName;

    @Value("${notification.registered.queue.name}")
    private String queueName;

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "registered-queue")
    public Queue queue() {
        return configurationHelper.getQueue(queueName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "registered-exchange")
    public FanoutExchange exchange() {
        return configurationHelper.getExchange(exchangeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "registered-binding")
    public Binding binding(@Qualifier("registered-queue") Queue queue,
            @Qualifier("registered-exchange") FanoutExchange exchange) {
        return configurationHelper.getBinding(queue, exchange);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "registered-callback")
    public MessageListenerAdapter messageListenerAdapter(Subscriber subscriber) {
        return configurationHelper.getListenerAdapter(subscriber, "registerNotificationRegistered");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "registered-callback-binding")
    public SimpleMessageListenerContainer eventListenerContainer(
            ConnectionFactory connectionFactory,
            @Qualifier("registered-callback") MessageListenerAdapter adapter) {
        return configurationHelper.getEventListenerContainer(connectionFactory, adapter, queueName);
    }
}
