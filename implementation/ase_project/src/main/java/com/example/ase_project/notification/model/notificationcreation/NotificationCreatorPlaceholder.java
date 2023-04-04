package com.example.ase_project.notification.model.notificationcreation;

import com.example.ase_project.notification.model.data.NotificationEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("placeholder")
public class NotificationCreatorPlaceholder implements INotificationCreator{
    public String create(NotificationEvent event) {
        return "Placeholder notification content";
    }

}
