package com.example.ase_project.notification.model.data;

import java.util.Date;

/**
 * Used to store relevant information for the notification domain about an event
 */
public class NotificationEvent {

    private String id;
    private String name;
    private String description;
    private Date eventDate;
    private EEventType type;

    public NotificationEvent() {
    }

    public NotificationEvent(String id, String name, String description, Date eventDate,
            EEventType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.eventDate = eventDate;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
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

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
