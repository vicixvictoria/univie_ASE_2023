package com.ase.notification;

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

/**
 * Spring configuration class, responsible for setting up RabbitMQ for this microservice. It
 * defines:
 * <ul>
 *     <li>An exchange with the name {@code event.bookmarked.exchange}, which receives
 *     information about bookmarked events</li>
 *     <li>An exchange with the name {@code event.registered.exchange}, which receives information
 *     about registered events</li>
 *     <li>A queue with the name {@code notification.queue.name}, which holds all messages coming
 *     from both exchanges mentioned above</li>
 * </ul>
 * All names are defined in the {@code application.properties}
 */
@Configuration
public class RabbitMQConfiguration {

    @Value("${event.bookmarked.exchange}")
    private String eventBookmarkedExchange;
    @Value("${event.registered.exchange}")
    private String eventRegisteredExchange;

    @Value("${notification.queue.name}")
    private String queueName;

    /**
     * Defines a {@link Queue} queue bean which will hold all messages for both defined exchanges
     *
     * @return {@link Queue} queue bean
     */
    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    /**
     * Defines an exchange for receiving <b>bookmarked</b> events. The defined exchange is a fanout
     * exchange (sends a copy of the message to each attached queue)
     *
     * @return {@link FanoutExchange} a bean defining the exchange
     */
    @Bean(name = "bookmarked_exchange")
    public FanoutExchange bookmarkedExchange() {
        return new FanoutExchange(eventBookmarkedExchange, true, false);
    }

    /**
     * Defines an exchange for receiving <b>registered</b> events. The defined exchange is a fanout
     * exchange (sends a copy of the message to each attached queue)
     *
     * @return {@link FanoutExchange} a bean defining the exchange
     */
    @Bean(name = "registered_exchange")
    public FanoutExchange registeredExchange() {
        return new FanoutExchange(eventRegisteredExchange, true, false);
    }

    /**
     * Binds the <b>bookmark</b> exchange to the defined queue.
     *
     * @param queue    {@link Queue} Autowired, the queue which will be bound
     * @param exchange {@link FanoutExchange} Autowired, the exchange to which the queue will be
     *                 bound
     * @return {@link Binding} a bean, containing the binding of the queue and the exchange
     */
    @Bean(name = "bookmarked_binding")
    public Binding bookmarkedBinding(Queue queue,
            @Qualifier("bookmarked_exchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    /**
     * Binds the <b>register</b> exchange to the defined queue.
     *
     * @param queue    {@link Queue} Autowired, the queue which will be bound
     * @param exchange {@link FanoutExchange} Autowired, the exchange to which the queue will be
     *                 bound
     * @return {@link Binding} a bean, containing the binding of the queue and the exchange
     */
    @Bean(name = "registered_binding")
    public Binding registeredBinding(Queue queue,
            @Qualifier("registered_exchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    /**
     * Defines the callback method called when a new event is bookmarked/registered
     *
     * @param subscriber {@link Subscriber} the class which defines the callback method
     * @return {@link MessageListenerAdapter} a bean of a wrapped callback method
     */
    @Bean(name = "register_notification_adapter")
    public MessageListenerAdapter registerNotificationAdapter(Subscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "registerNotification");
    }

    /**
     * Defines the callback method called when an event is updated
     *
     * @param subscriber {@link Subscriber} Autowired, the class which defines the callback method
     * @return {@link MessageListenerAdapter} a bean of a wrapped callback method
     */
    @Bean(name = "update_event_adapter")
    public MessageListenerAdapter updateEventAdapter(Subscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "eventUpdated");
    }

    /**
     * Binds the callback method to the defined queue
     * <p>
     * TODO: HOW DO I HANDLE UPDATES?
     *
     * @param connectionFactory {@link ConnectionFactory} Autowired, created by spring magic
     * @param listenerAdapter   {@link MessageListenerAdapter} Autowired, wraps the callback method
     * @return {@link SimpleMessageListenerContainer} a bean, binding the callback method to the
     * defined queue
     */

    @Bean
    public SimpleMessageListenerContainer registerNotificationContainer(
            ConnectionFactory connectionFactory,
            @Qualifier("register_notification_adapter") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }
}
