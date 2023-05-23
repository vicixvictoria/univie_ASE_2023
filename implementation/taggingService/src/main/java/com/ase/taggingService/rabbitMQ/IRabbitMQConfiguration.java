package com.ase.taggingService.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

/**
 * Defines the required methods that need to be implemented by a RabbitMQ configuration class.
 * <p>
 * Not that some methods are documented to return Spring Beans. This only happens in the concrete
 * implementations and not in this interface.
 */
public interface IRabbitMQConfiguration<Subscriber> {

    /**
     * Defines a rabbitMQ queue and returns it as a Spring Bean
     *
     * @return {@link Queue} The created rabbitMQ queue used as a Bean
     */
    Queue queue();

    /**
     * Defines a rabbitMQ exchange of the type fanout and returns it as a Spring Bean
     *
     * @return {@link FanoutExchange} The created rabbitMQ exchange used as a Bean
     */
    FanoutExchange exchange();

    /**
     * Binds the given queue to the given exchange and returns the binding as a Spring Bean
     *
     * @param queue    {@link Queue} a rabbitMQ queue
     * @param exchange {@link FanoutExchange} a rabbitMQ exchange
     * @return {@link Binding} binding between the queue and exchange as a Spring Bean
     */
    Binding binding(Queue queue, FanoutExchange exchange);

    /**
     * Defines a listener adapter which can be used to process incoming rabbitMQ messages
     *
     * @param subscriber the class which defines the callback method
     * @return {@link MessageListenerAdapter} a Spring Bean containing the callback method
     */
    MessageListenerAdapter messageListenerAdapter(Subscriber subscriber);

    /**
     * Binds the defined {@link MessageListenerAdapter} to a queue (the queue is a class parameter)
     *
     * @param connectionFactory {@link ConnectionFactory} a magic Spring class which is
     *                          automatically created and can be autowired
     * @param adapter           {@link MessageListenerAdapter} the listener adapter to be bound to a
     *                          queue
     * @return {@link SimpleMessageListenerContainer} the bound listener adapter to a java queue as
     * a Spring Bean
     */
    SimpleMessageListenerContainer eventListenerContainer(ConnectionFactory connectionFactory,
            MessageListenerAdapter adapter);
}

