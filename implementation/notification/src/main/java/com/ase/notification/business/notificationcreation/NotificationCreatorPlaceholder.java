package com.ase.notification.business.notificationcreation;

import com.ase.notification.domain.NotificationEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("placeholder")
public class NotificationCreatorPlaceholder implements INotificationCreator {

    /**
     * @inheritDoc
     */
    public String create(NotificationEvent event) {
        return "Placeholder notification content";
    }

}
