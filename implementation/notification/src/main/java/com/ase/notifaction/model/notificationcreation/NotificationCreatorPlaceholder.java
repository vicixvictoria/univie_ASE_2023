package com.ase.notifaction.model.notificationcreation;

import com.ase.notifaction.model.data.NotificationEvent;
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
