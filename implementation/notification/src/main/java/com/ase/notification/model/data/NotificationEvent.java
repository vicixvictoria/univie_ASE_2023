package com.ase.notification.model.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import java.util.UUID;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Contains all relevant information about a notification.
 */
@Entity
public class NotificationEvent {
    @Id
    private String id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RawEvent event;
    private String userId;  // can easily be extended to hold a user. I don't know how it will work yet
    private EEventType type;

    public NotificationEvent() {
    }

    public NotificationEvent(String id, RawEvent event, String userId, EEventType type) {
        this.id = id;
        this.event = event;
        this.userId = userId;
        this.type = type;
    }

    public NotificationEvent(RawEvent event, String userId, EEventType type) {
        this.id = UUID.randomUUID().toString();
        this.event = event;
        this.userId = userId;
        this.type = type;
    }

    public IUnmodifiableRawEvent getEvent() {
        return event;
    }

    public void setEvent(RawEvent event) {
        this.event = event;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String eventId) {
        this.userId = userId;
    }

    public EEventType getType() {
        return type;
    }

    public void setType(EEventType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationEvent that)) {
            return false;
        }
        return getEvent().equals(that.getEvent()) && getUserId().equals(that.getUserId())
                && getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEvent(), getUserId(), getType());
    }
}
