package com.ase.notification.model.repository;

import com.ase.notification.model.data.IUnmodifiableRawEvent;
import com.ase.notification.model.data.NotificationEvent;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INotificationEventRepository extends JpaRepository<NotificationEvent, String> {
    Optional<Collection<NotificationEvent>> findNotificationEventsByEvent(
            IUnmodifiableRawEvent event);
}
