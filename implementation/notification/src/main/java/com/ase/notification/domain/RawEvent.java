package com.ase.notification.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;

/**
 * Used to store a copy of Events. The global Event is reduced to only hold a relevant subset of
 * information
 */
@Entity
public class RawEvent implements IUnmodifiableRawEvent {

    @Id
    private String id;
    private String name;
    private String description;
    private Date eventDate;

    public RawEvent() {
    }

    public RawEvent(String id, String name, String description, Date eventDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.eventDate = eventDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public void update(IUnmodifiableRawEvent event) {
        this.name = event.getName();
        this.description = event.getDescription();
        this.eventDate = event.getEventDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IUnmodifiableRawEvent that)) {
            return false;
        }

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
