package com.ase.attendance.integration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.ase.common.RabbitMQMessage;
import com.ase.common.EMessageType;
import com.ase.common.attendance.AttendanceMessage;

/**
 * The Publisher class is responsible for sending messages to the RabbitMQ message broker.
 */
@Component
public class Publisher {
    @Value("${attendance.exchange}")
    private String attendanceExchange;
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
     * Publishes a new attendee message to the RabbitMQ message broker.
     *
     * @param userID the ID of the user who has attended an event.
     * @param eventID the ID of the event that has been attended.
     */
    public void newAttendee(String userID, String eventID) {
        AttendanceMessage aMessage = new AttendanceMessage(userID, eventID);
        RabbitMQMessage<AttendanceMessage> message = new RabbitMQMessage<>(EMessageType.NEW, aMessage);
        rabbitTemplate.convertAndSend(attendanceExchange, "", message);
    }

    public void deleteAttendee(String userID, String eventID) {
        AttendanceMessage aMessage = new AttendanceMessage(userID, eventID);
        RabbitMQMessage<AttendanceMessage> message = new RabbitMQMessage<>(EMessageType.DELETE, aMessage);
        rabbitTemplate.convertAndSend(attendanceExchange, "", message);
    }
}
