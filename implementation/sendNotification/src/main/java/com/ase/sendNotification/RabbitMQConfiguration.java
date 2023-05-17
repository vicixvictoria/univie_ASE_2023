package com.ase.sendNotification;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A spring configuration class, responsible for setting up RabbitMQ for the sendNotifcation
 * microservice. It sets up:
 * <ul>
 *     <li>an exchange with the name defined in {@code sendnotification.exchange}</li>
 *     <li>a queue attached to the exchange with the name {@code sendnotification.queue}</li>
 * </ul>
 * All names are defined in the {@code application.properties}
 */
@Configuration
public class RabbitMQConfiguration {

    @Value("${sendnotification.exchange}")
    private String exchangeName;
    @Value("${sendnotification.queue}")
    private String queueName;

    /**
     * Defines a bean with the queue where this microservice will be listening
     *
     * @return {@link Queue} the created queue
     */
    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    /**
     * Defines a bean with the exchange where this microservice will be listening. The defined
     * exchange is a fanout exchange (sends a copy of the message to every attached queue)
     *
     * @return {@link FanoutExchange} the created exchange
     */
    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(exchangeName, true, false);
    }

    /**
     * Binds the created queue with the created exchange
     *
     * @param queue    {@link Queue} autowired queue bean
     * @param exchange {@link FanoutExchange} autowired FanoutExchange bean
     * @return {@link Binding} the created binding between the queue and the exchange
     */
    @Bean
    public Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    /**
     * A bean which defines which method will get called when a message arrives
     *
     * @param subscriber {@link Subscriber} the class, which defines the method from which a
     *                   notification will be sent
     * @return {@link MessageListenerAdapter} a bean which defines a method which will be called
     * when a message arrives
     */
    @Bean
    public MessageListenerAdapter messageListenerAdapter(Subscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "sendMessage");
    }

    /**
     * Binds the defined method defined to receive a message to a specific queue.
     *
     * @param connectionFactory      {@link ConnectionFactory} Autowired class, created by default
     *                               via spring magic
     * @param messageListenerAdapter {@link MessageListenerAdapter} wraps the methods which will be
     *                               called when a message arrives
     * @return {@link SimpleMessageListenerContainer} the binding of the subscriber method and the
     * queue
     */
    @Bean
    public SimpleMessageListenerContainer bindSubscriber(ConnectionFactory connectionFactory,
            MessageListenerAdapter messageListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(queue());
        container.setMessageListener(messageListenerAdapter);
        return container;
    }

}
