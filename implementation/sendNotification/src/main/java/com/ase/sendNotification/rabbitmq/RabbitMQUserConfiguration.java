package com.ase.sendNotification.rabbitmq;

import com.ase.sendNotification.Subscriber;
import com.ase.sendNotification.rabbitmq.helper.IRabbitMQConfiguration;
import com.ase.sendNotification.rabbitmq.helper.RabbitMQConfigurationHelper;
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
public class RabbitMQUserConfiguration implements IRabbitMQConfiguration<Subscriber> {

    @Value("${user.exchange}")
    private String exchangeName;

    @Value("${sendnotification.user.queue}")
    private String queueName;

    private final RabbitMQConfigurationHelper<Subscriber> configurationHelper = new RabbitMQConfigurationHelper<Subscriber>();

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "user-queue")
    public Queue queue() {
        return configurationHelper.getQueue(queueName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "user-exchange")
    public FanoutExchange exchange() {
        return configurationHelper.getExchange(exchangeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "user-binding")
    public Binding binding(@Qualifier("user-queue") Queue queue,
            @Qualifier("user-exchange") FanoutExchange exchange) {
        return configurationHelper.getBinding(queue, exchange);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "user-adapter")
    public MessageListenerAdapter messageListenerAdapter(Subscriber subscriber) {
        return configurationHelper.getListenerAdapter(subscriber, "userConsumer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "user-adapter-binding")
    public SimpleMessageListenerContainer eventListenerContainer(
            ConnectionFactory connectionFactory,
            @Qualifier("user-adapter") MessageListenerAdapter adapter) {
        return configurationHelper.getEventListenerContainer(connectionFactory, adapter, queueName);
    }
}
