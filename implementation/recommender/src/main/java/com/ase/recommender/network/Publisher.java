package com.ase.recommender.network;
import java.util.List;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.ase.common.RabbitMQMessage;
import com.ase.common.EMessageType;
import com.ase.common.recommender.RecommenderMessage;


@Component
public class Publisher {
    @Value("${recommender.exchange}")
    private String recommenderExchange;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public Publisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void recommend(List<String> userIDs) {
        RecommenderMessage rMessage = new RecommenderMessage(userIDs);
        RabbitMQMessage<RecommenderMessage> message = new RabbitMQMessage<>(EMessageType.UPDATE, rMessage);
        rabbitTemplate.convertAndSend(recommenderExchange, "", message);
    }
}
