package com.ase.notification.model.repository;

import com.ase.notification.model.data.NotificationEvent;
import com.ase.notification.model.data.NotificationUser;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("local")
public class NotificationRepositoryLocal implements INotificationRepository {

    private final Map<NotificationEvent, Set<NotificationUser>> eventUserStore = new HashMap<>();

    private void createEntryIfEmpty(NotificationEvent event) {
        if (!eventUserStore.containsKey(event)) {
            eventUserStore.put(event, new HashSet<>());
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public Collection<NotificationEvent> getEvents(String userId) {
        // TODO: Custom exception

        assert userId != null;

        return eventUserStore.entrySet().stream()
                .filter(entry -> entry.getValue().stream()
                        .map(NotificationUser::getId)
                        .anyMatch(ele -> ele.equals(userId)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * @inheritDoc
     */
    // TODO: rename me
    @Override
    public void addEvent(NotificationUser user, NotificationEvent event) {

        createEntryIfEmpty(event);

        assert eventUserStore.containsKey(event);

        eventUserStore.get(event).add(user);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Collection<NotificationUser> updateEvent(NotificationEvent event) {

        // if the event is not contain simply create an entry for it
        createEntryIfEmpty(event);

        return Collections.unmodifiableSet(eventUserStore.get(event));
    }
}
