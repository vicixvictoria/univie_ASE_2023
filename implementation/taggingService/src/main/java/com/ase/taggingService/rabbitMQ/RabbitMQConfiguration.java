package com.ase.taggingService.rabbitMQ;

import com.ase.taggingService.Subscriber;
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


    @Value("${taggingEvent.frontend.queue}")
    private String queueName;
    @Value("${taggingEvent.frontend_exchange}")
    private String exchangeName;

    /**
     * Queue for frontend exchange, for adding, updating and deleting taggingEvents
     */
    @Override
    @Bean(name = "taggingEvent-frontend")
    public Queue queue() {
        return rabbitMQConfigurationHelper.getQueue(queueName);
    }

    /**
     * exchange for taggingEvent
     */
    @Override
    @Bean(name = "taggingEvent-exchange")
    public FanoutExchange exchange() {
        return rabbitMQConfigurationHelper.getExchange(exchangeName);
    }

    /**
     * Binding for tagging exchange and queue
     */
    @Override
    @Bean(name = "taggingEvent-taggingEventFrontendBinding")
    public Binding binding(@Qualifier("taggingEvent-frontend") Queue queue,
            @Qualifier("taggingEvent-exchange") FanoutExchange exchange) {
        return rabbitMQConfigurationHelper.getBinding(queue, exchange);
    }

    /**
     * @param subscriber defined in the Consumer
     * @return MessageListenerAdapter implemented from Subscriber
     */
    @Override
    @Bean(name = "taggingEvent-taggingEventAdapter")
    public MessageListenerAdapter messageListenerAdapter(Subscriber subscriber) {
        return rabbitMQConfigurationHelper.getListenerAdapter(subscriber,
                "bookmarkEventConsumer"); //implemnts form subscriber
    }

    /**
     * @param connectionFactory, MessageListenerAdapter
     * @return SimpleMessageListenerContainer
     */
    @Override
    @Bean(name = "taggingEvent-taggingEventListener")
    public SimpleMessageListenerContainer eventListenerContainer(
            ConnectionFactory connectionFactory,
            @Qualifier("taggingEvent-taggingEventAdapter") MessageListenerAdapter adapter) {
        return rabbitMQConfigurationHelper.getEventListenerContainer(connectionFactory, adapter,
                queueName);
    }
}
