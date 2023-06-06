package com.ase.event.integration.helper;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

public class RabbitMQConfigurationHelper<Subscriber> {

    public Queue getQueue(String queueName) {
        return new Queue(queueName, true);
    }

    public FanoutExchange getExchange(String exchangeName) {
        return new FanoutExchange(exchangeName, true, false);
    }

    public Binding getBinding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    public MessageListenerAdapter getListenerAdapter(Subscriber subscriber, String methodName) {
        return new MessageListenerAdapter(subscriber, methodName);
    }

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
