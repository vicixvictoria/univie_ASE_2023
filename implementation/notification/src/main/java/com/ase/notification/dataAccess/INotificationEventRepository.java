package com.ase.notification.dataAccess;

import com.ase.notification.domain.EEventType;
import com.ase.notification.domain.IUnmodifiableRawEvent;
import com.ase.notification.domain.NotificationEvent;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INotificationEventRepository extends JpaRepository<NotificationEvent, String> {

    Optional<Collection<NotificationEvent>> findNotificationEventsByEvent(
            IUnmodifiableRawEvent event);

    Optional<NotificationEvent> findNotificationEventsByEventAndUserIdAndType(IUnmodifiableRawEvent event,
            String userId, EEventType type);
}
