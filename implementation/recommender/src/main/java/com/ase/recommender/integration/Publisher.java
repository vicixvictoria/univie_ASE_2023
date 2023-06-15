package com.ase.recommender.integration;
import java.util.List;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.ase.common.RabbitMQMessage;
import com.ase.common.EMessageType;
import com.ase.common.recommender.RecommenderMessage;
import com.ase.common.sendNotification.NotificationContent;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Publisher class handles publishing messages to RabbitMQ.
 */
@Component
public class Publisher {
    @Value("${sendnotification.exchange}")
    private String notificationExchange;
    private final RabbitTemplate rabbitTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());
    @Value("${recommender.exchange}")
    private String recommenderExchange;


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
     Sends a recommendation message to RabbitMQ for the specified event ID and list of user IDs.
     Each user ID in the list will receive a notification with the recommended event ID.
     @param eventID the ID of the event to recommend
     @param userIDs the list of user IDs to notify
     */
    public void recommend(String eventID, List<String> userIDs) {
        LOGGER.info("New Recommendation for Event ID: " + eventID + ", recommended User IDs: " + userIDs);
        RecommenderMessage rMessage = new RecommenderMessage(eventID, userIDs);
        RabbitMQMessage<RecommenderMessage> message = new RabbitMQMessage<>(EMessageType.UPDATE, rMessage);
        rabbitTemplate.convertAndSend(recommenderExchange, "", message);
        for(int i = 0; i < userIDs.size(); i++)
        {
            NotificationContent nMessage = new NotificationContent(userIDs.get(i), "Dear User, this eventID was recommended to you based on your event interests: " + eventID);
            RabbitMQMessage<NotificationContent> notification = new RabbitMQMessage<>(EMessageType.NEW, nMessage);
            rabbitTemplate.convertAndSend(notificationExchange, "", notification);
        }
    }
}
