package com.ase.recommender.integration;
import java.util.List;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.ase.common.RabbitMQMessage;
import com.ase.common.EMessageType;
import com.ase.common.recommender.RecommenderMessage;

/**
 * The Publisher class handles publishing messages to RabbitMQ.
 */
@Component
public class Publisher {
    @Value("${recommender.exchange}")
    private String recommenderExchange;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Constructs a new Publisher with a given RabbitTemplate.
     *
     * @param rabbitTemplate the RabbitTemplate to be used by the Publisher.
     */
    @Autowired
    public Publisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publishes a list of user IDs as a recommendation message to RabbitMQ.
     *
     * @param userIDs The list of user IDs to recommend.
     */
    public void recommend(List<String> userIDs) {
        RecommenderMessage rMessage = new RecommenderMessage(userIDs);
        RabbitMQMessage<RecommenderMessage> message = new RabbitMQMessage<>(EMessageType.UPDATE, rMessage);
        rabbitTemplate.convertAndSend(recommenderExchange, "", message);
    }
}
