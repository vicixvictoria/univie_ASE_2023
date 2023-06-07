package com.ase.attendance.integration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

/**
 * The RabbitMQConfigurationHelper class provides methods to assist in setting up RabbitMQ configurations.
 */
public class RabbitMQConfigurationHelper {

    /**
     * Creates a new queue with the given name.
     *
     * @param queueName the name of the queue to be created.
     * @return the created queue.
     */
    public Queue getQueue(String queueName) {
        return new Queue(queueName, true);
    }

    /**
     * Creates a new fanout exchange with the given name.
     *
     * @param exchangeName the name of the exchange to be created.
     * @return the created fanout exchange.
     */
    public FanoutExchange getExchange(String exchangeName) {
        return new FanoutExchange(exchangeName, true, false);
    }

    /**
     * Creates a new binding between a queue and a fanout exchange.
     *
     * @param queue the queue to be bound.
     * @param exchange the exchange to bind the queue to.
     * @return the created binding.
     */
    public Binding getBinding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    /**
     * Creates a new MessageListenerAdapter for a given subscriber.
     *
     * @param subscriber the Subscriber to be used by the message listener adapter.
     * @param methodName the name of the method to be used when messages are received.
     * @return the created message listener adapter.
     */
    public MessageListenerAdapter getListenerAdapter(Subscriber subscriber, String methodName) {
        return new MessageListenerAdapter(subscriber, methodName);
    }

    /**
     * Creates and configures a SimpleMessageListenerContainer for event listeners.
     *
     * @param connectionFactory The connection factory to use for creating connections.
     * @param adapter The message listener adapter for handling messages.
     * @param queueName The name of the queue to listen to.
     * @return The configured event listener container.
     */
    public SimpleMessageListenerContainer getEventListenerContainer(
            ConnectionFactory connectionFactory,
            MessageListenerAdapter adapter,
            String queueName) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(adapter);
        return container;
    }
}
