package com.ase.notification.domain;

import org.springframework.stereotype.Component;

@Component
public class NotificationEventFactory {

    public NotificationEvent get(RawEvent event, String userId, EEventType type) {
        return new NotificationEvent(event, userId, type);
    }
}
