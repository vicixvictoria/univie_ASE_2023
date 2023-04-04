package com.example.ase_project.notification.model.repository;

import com.example.ase_project.notification.model.data.NotificationEvent;
import com.example.ase_project.notification.model.data.NotificationUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Qualifier("local")
public class NotificationRepositoryLocal implements INotificationRepository {

    private final Map<NotificationUser, Set<NotificationEvent>> userEventStore = new HashMap<>();
    private final Map<String, NotificationUser> userStore = new HashMap<>();

    @Override
    public Collection<NotificationEvent> getEvents(String userId) {
        // TODO: Custom exception
        NotificationUser user = userStore.get(userId);
        assert userEventStore.containsKey(user);

        return Collections.unmodifiableSet(userEventStore.get(user));
    }

    @Override
    public void addEvent(String userId, NotificationEvent event) {

        if (!userStore.containsKey(userId)) {
            NotificationUser newUser = new NotificationUser(); // TODO: how do I get the proper object
            newUser.setId(userId);

            userStore.put(userId, newUser);
            userEventStore.put(newUser, new HashSet<>());
        }

        NotificationUser user = userStore.get(userId);
        assert userEventStore.containsKey(user);

        userEventStore.get(user).add(event);
    }

    @Override
    public Collection<NotificationUser> getUser() {
        return Collections.unmodifiableSet(userEventStore.keySet());
    }

    @Override
    public NotificationUser getUserById(String userId) {
        return userStore.get(userId);
    }

    @Override
    public Collection<NotificationUser> updateEvent(NotificationEvent event) {
        // find all updated users
        var update = userEventStore.entrySet().stream().filter(entry -> entry.getValue().contains(event)).collect(Collectors.toSet());

        update.stream().forEach(entry -> {
            // TODO: this is pretty bad, I should probably use a different data structure here
            //  the idea right now is that the new and old event have the same ID, and thus equal
            //  So, I remove the old one and add the new one.
            entry.getValue().remove(event);
            entry.getValue().add(event);
        });

        return update.stream().map(Map.Entry::getKey).collect(Collectors.toUnmodifiableSet());
    }
}
