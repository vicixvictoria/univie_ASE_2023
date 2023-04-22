package com.example.ase_project.notification.model.notificationsendtime;

import com.example.ase_project.notification.model.data.NotificationEvent;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("day-before")
public class NotificationSentTimeDayBefore implements INotificationSendTime {

    /**
     * @inheritDoc
     */
    @Override
    public Instant get(NotificationEvent event) {
        return event.getEventDate().toInstant()
                .minusSeconds(ENotificationConstants.DAY_SECONDS.get());
    }
}
