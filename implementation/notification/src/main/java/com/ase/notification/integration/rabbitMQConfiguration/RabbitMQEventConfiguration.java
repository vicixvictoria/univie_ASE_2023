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
public class RabbitMQEventConfiguration implements IRabbitMQConfiguration {

    private final RabbitMQConfigurationHelper configurationHelper = new RabbitMQConfigurationHelper();
    @Value("${event.exchange}")
    private String exchangeName;

    @Value("${notification.event.queue.name}")
    private String queueName;

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "event-queue")
    public Queue queue() {
        return configurationHelper.getQueue(queueName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "event-exchange")
    public FanoutExchange exchange() {
        return configurationHelper.getExchange(exchangeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "event-binding")
    public Binding binding(@Qualifier("event-queue") Queue queue,
            @Qualifier("event-exchange") FanoutExchange exchange) {
        return configurationHelper.getBinding(queue, exchange);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "event-callback")
    public MessageListenerAdapter messageListenerAdapter(Subscriber subscriber) {
        return configurationHelper.getListenerAdapter(subscriber, "eventConsumer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "event-callback-binding")
    public SimpleMessageListenerContainer eventListenerContainer(
            ConnectionFactory connectionFactory,
            @Qualifier("event-callback") MessageListenerAdapter adapter) {
        return configurationHelper.getEventListenerContainer(connectionFactory, adapter, queueName);
    }
}
