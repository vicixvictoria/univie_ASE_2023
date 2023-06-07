package com.ase.sendNotification.integration;

import com.ase.sendNotification.integration.helper.IRabbitMQConfiguration;
import com.ase.sendNotification.integration.helper.RabbitMQConfigurationHelper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
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
public class RabbitMQSendNotificationConfiguration implements IRabbitMQConfiguration<Subscriber> {

    @Value("${sendnotification.exchange}")
    private String exchangeName;
    @Value("${sendnotification.queue}")
    private String queueName;

    private final RabbitMQConfigurationHelper<Subscriber> configurationHelper = new RabbitMQConfigurationHelper<>();

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "sendNotification-queue")
    public Queue queue() {
        return configurationHelper.getQueue(queueName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "sendNotification-exchange")
    public FanoutExchange exchange() {
        return configurationHelper.getExchange(exchangeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "sendNotification-binding")
    public Binding binding(@Qualifier("sendNotification-queue") Queue queue,
            @Qualifier("sendNotification-exchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "sendNotification-adapter")
    public MessageListenerAdapter messageListenerAdapter(Subscriber subscriber) {
        return configurationHelper.getListenerAdapter(subscriber, "sendNotification");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Bean(name = "sendNotification-adapter-binding")
    public SimpleMessageListenerContainer eventListenerContainer(
            ConnectionFactory connectionFactory,
            @Qualifier("sendNotification-adapter") MessageListenerAdapter messageListenerAdapter) {
        return configurationHelper.getEventListenerContainer(connectionFactory,
                messageListenerAdapter, queueName);
    }

}
