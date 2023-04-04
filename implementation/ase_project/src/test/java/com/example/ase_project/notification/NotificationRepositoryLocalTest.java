package com.example.ase_project.notification;

import com.example.ase_project.notification.model.data.NotificationEvent;
import com.example.ase_project.notification.model.data.NotificationUser;
import com.example.ase_project.notification.model.repository.NotificationRepositoryLocal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.stream.Collectors;

public class NotificationRepositoryLocalTest {

    private NotificationEvent event1;
    private NotificationEvent event2;

    @BeforeEach
    public void setEvents() {
        event1 = Mockito.mock(NotificationEvent.class);
        event2 = Mockito.mock(NotificationEvent.class);
    }

    @Test
    public void NotificationRepositoryLocal_addEvents_validCall() {
        NotificationRepositoryLocal repository = new NotificationRepositoryLocal();

        String id1 = "some-id";
        String id2 = "other-id";

        Executable test = () -> {
            repository.addEvent(id1, event1);
            repository.addEvent(id2, event2);
        };

        Assertions.assertDoesNotThrow(test);
    }

    @Test
    public void NotificationRepositoryLocal_addEvents_invalidId() {
    }

    @Test
    public void NotificationRepositoryLocal_getEvents_validCallAndTest() {
        NotificationRepositoryLocal repository = new NotificationRepositoryLocal();

        String id1 = "some-id";
        String id2 = "other-id";

        repository.addEvent(id1, event1);
        repository.addEvent(id2, event2);

        var events1 = repository.getEvents(id1);
        var events2 = repository.getEvents(id2);

        Assertions.assertEquals(events1.size(), 1);
        Assertions.assertEquals(events2.size(), 1);

        // we can just get here as we already asserted size 1
        Assertions.assertEquals(events1.stream().findFirst().get(), event1);
        Assertions.assertEquals(events2.stream().findFirst().get(), event2);
    }

    @Test
    public void NotificationRepositoryLocal_getEvents_invalidId() {
        // TODO: refactor in
    }

    @Test
    public void NotificationRepositoryLocal_getUser_validCall() {
        NotificationRepositoryLocal repository = new NotificationRepositoryLocal();

        String id1 = "some-id";
        String id2 = "other-id";

        repository.addEvent(id1, event1);
        repository.addEvent(id2, event2);

        Collection<NotificationUser> users = repository.getUser();
        Collection<String> usersId = users.stream().map(NotificationUser::getId).collect(Collectors.toSet());

        Assertions.assertTrue(usersId.contains(id1));
        Assertions.assertTrue(usersId.contains(id2));
        Assertions.assertEquals(2, users.size());
    }

    @Test
    public void NotificationRepositoryLocal_getUserById_validCall() {
        NotificationRepositoryLocal repository = new NotificationRepositoryLocal();

        String id1 = "some-id";
        repository.addEvent(id1, event1);

        NotificationUser user = repository.getUserById(id1);

        Assertions.assertEquals(user.getId(), id1);
    }
}
