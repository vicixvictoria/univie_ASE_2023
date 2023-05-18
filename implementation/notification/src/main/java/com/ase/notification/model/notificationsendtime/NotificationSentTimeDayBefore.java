package com.ase.notification.model.notificationsendtime;

import com.ase.notification.model.data.NotificationEvent;
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
        return event.getEvent().getEventDate().toInstant()
                .minusSeconds(ENotificationConstants.DAY_SECONDS.get());
    }
}
