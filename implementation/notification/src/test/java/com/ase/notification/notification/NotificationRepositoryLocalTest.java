package com.ase.notification.notification;

import com.ase.notification.model.data.NotificationEvent;
import com.ase.notification.model.data.NotificationUser;
import com.ase.notification.model.repository.NotificationRepositoryLocal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

public class NotificationRepositoryLocalTest {

    private NotificationEvent event1;
    private NotificationEvent event2;

    private NotificationUser getUser(String id) {
        NotificationUser user = new NotificationUser();
        user.setId(id);

        return user;
    }

    @BeforeEach
    public void setEvents() {
        event1 = Mockito.mock(NotificationEvent.class);
        event2 = Mockito.mock(NotificationEvent.class);
    }

    @Test
    public void NotificationRepositoryLocal_addEvents_validCall() {
        NotificationRepositoryLocal repository = new NotificationRepositoryLocal();

        NotificationUser user1 = getUser("some-id");
        NotificationUser user2 = getUser("other-id");

        Executable test = () -> {
            repository.addEvent(user1, event1);
            repository.addEvent(user2, event2);
        };

        Assertions.assertDoesNotThrow(test);
    }

    @Test
    public void NotificationRepositoryLocal_getEvents_validCallAndTest() {
        NotificationRepositoryLocal repository = new NotificationRepositoryLocal();

        NotificationUser user1 = getUser("some-id");
        NotificationUser user2 = getUser("other-id");

        repository.addEvent(user1, event1);
        repository.addEvent(user2, event2);

        var events1 = repository.getEvents(user1.getId());
        var events2 = repository.getEvents(user2.getId());

        Assertions.assertEquals(1, events1.size());
        Assertions.assertEquals(1, events2.size());

        // we can just get here as we already asserted size 1
        Assertions.assertEquals(events1.stream().findFirst().get(), event1);
        Assertions.assertEquals(events2.stream().findFirst().get(), event2);
    }

}
