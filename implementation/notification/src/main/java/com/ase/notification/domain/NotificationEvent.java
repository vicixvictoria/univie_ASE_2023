package com.ase.notification.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Contains all relevant information about a notification.
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueRow", columnNames = {"eventId", "userId", "type"})
})
public class NotificationEvent {

    @Id
    private String id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "eventId")
    private RawEvent event;

    @Column
    private String userId; // only save a userId here, we don't need the entire user object

    @Column
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setUserId(String userId) {
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
        return getId().hashCode();
    }
}
