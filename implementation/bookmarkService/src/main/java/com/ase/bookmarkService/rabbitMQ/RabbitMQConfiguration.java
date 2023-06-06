package com.ase.bookmarkService.rabbitMQ;

import com.ase.bookmarkService.Subscriber;
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
public class RabbitMQConfiguration implements IRabbitMQConfiguration<Subscriber> {

    private RabbitMQConfigurationHelper<Subscriber> rabbitMQConfigurationHelper = new RabbitMQConfigurationHelper<>();

    @Value("${bookmarkEvent.frontend.queue}")
    private String queueName;
    @Value("${bookmarkEvent.frontend_exchange}")
    private String exchangeName; // injection from properties

    /**
     * Queue for frontend exchange, for adding and deleting bookmarkEvents
     */
    @Override
    @Bean(name = "bookmarkEvent-frontend")
    public Queue queue() {
        return rabbitMQConfigurationHelper.getQueue(queueName);
    }

    /**
     * exchange for bookmarkEvents
     */
    @Override
    @Bean(name = "bookmarkEvent-exchange")
    public FanoutExchange exchange() {
        return rabbitMQConfigurationHelper.getExchange(exchangeName);
    }

    /**
     * Binding for bookmark exchange and queue
     */
    @Override
    @Bean(name = "bookmarkEvent-bookmarkEventFrontendBinding")
    public Binding binding(@Qualifier("bookmarkEvent-frontend") Queue queue,
            @Qualifier("bookmarkEvent-exchange") FanoutExchange exchange) {
        return rabbitMQConfigurationHelper.getBinding(queue, exchange);
    }

    /**
     * @param subscriber defined in the Consumer
     * @return MessageListenerAdapter implemented from Subscriber
     */
    @Override
    @Bean(name = "bookmarkEvent-bookmarkEventAdapter")
    public MessageListenerAdapter messageListenerAdapter(Subscriber subscriber) {
        return rabbitMQConfigurationHelper.getListenerAdapter(subscriber, "bookmarkEventConsumer");
    }

    /**
     * @param connectionFactory, MessageListenerAdapter
     * @return SimpleMessageListenerContainer
     */
    @Override
    @Bean(name = "bookmarkEvent-bookmarkEventListener")
    public SimpleMessageListenerContainer eventListenerContainer(
            ConnectionFactory connectionFactory,
            @Qualifier("bookmarkEvent-bookmarkEventAdapter") MessageListenerAdapter adapter) {
        return rabbitMQConfigurationHelper.getEventListenerContainer(connectionFactory, adapter,
                queueName);
    }
}

