package com.ase.searchServiceEvents.Network;

import com.ase.searchServiceEvents.Network.Helper.IRabbitMQConfiguration;
import com.ase.searchServiceEvents.Network.Helper.RabbitMQConfigurationHelper;
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
public class RabbitMQEventConfiguration implements IRabbitMQConfiguration<Subscriber> {

    @Value("${event.exchange}")
    private String exchangeName;

    @Value("${searchService.event.queue}")
    private String queueName;

    private final RabbitMQConfigurationHelper<Subscriber> configurationHelper = new RabbitMQConfigurationHelper<Subscriber>();


    @Override
    @Bean(name = "event-queue")
    public Queue queue() {
        return configurationHelper.getQueue(queueName);
    }


    @Override
    @Bean(name = "event-exchange")
    public FanoutExchange exchange() {
        return configurationHelper.getExchange(exchangeName);
    }


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
    @Bean(name = "event-adapter")
    public MessageListenerAdapter messageListenerAdapter(Subscriber subscriber) {
        return configurationHelper.getListenerAdapter(subscriber, "eventConsumer");
    }

    @Override
    @Bean(name = "event-adapter-binding")
    public SimpleMessageListenerContainer eventListenerContainer(
            ConnectionFactory connectionFactory,
            @Qualifier("event-adapter") MessageListenerAdapter adapter) {
        return configurationHelper.getEventListenerContainer(connectionFactory, adapter, queueName);
    }
}
