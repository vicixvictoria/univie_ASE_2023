package com.ase.attendance.network;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.ase.common.RabbitMQMessage;
import com.ase.common.EMessageType;
import com.ase.common.attendance.AttendanceMessage;

@Component
public class Publisher {
    @Value("${attendance.exchange}")
    private String attendanceExchange;
    private final RabbitTemplate rabbitTemplate;


    @Autowired
    public Publisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void newAttendee(String userID, String eventID) {
        AttendanceMessage aMessage = new AttendanceMessage(userID, eventID);
        RabbitMQMessage<AttendanceMessage> message = new RabbitMQMessage<>(EMessageType.UPDATE, aMessage);
        rabbitTemplate.convertAndSend(attendanceExchange, "", message);

    }


}
