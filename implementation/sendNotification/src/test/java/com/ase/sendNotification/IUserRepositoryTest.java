package com.ase.sendNotification;

import com.ase.sendNotification.model.NotificationUser;
import com.ase.sendNotification.rabbitmq.RabbitMQSendNotificationConfiguration;
import com.ase.sendNotification.rabbitmq.RabbitMQUserConfiguration;
import com.ase.sendNotification.repository.IUserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.UncategorizedDataAccessException;

@DataJpaTest
public class IUserRepositoryTest {

    @MockBean
    private ConnectionFactory connectionFactory;

    @MockBean
    private RabbitMQSendNotificationConfiguration sendNotificationConfiguration;

    @MockBean
    private RabbitMQUserConfiguration userConfiguration;


    private final String id = "someid";
    private final String email = "some@email.com";

    @Autowired
    IUserRepository userRepository;

    @Test
    public void IUserRepository_insertNotificationUser_validCall() {
        NotificationUser user = new NotificationUser(id, email);
        userRepository.save(user);
    }

    @Test
    public void IUserRepository_insertNotificationUser_checkContains() {
        NotificationUser user = new NotificationUser(id, email);
        userRepository.save(user);
        Assertions.assertTrue(userRepository.existsById(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 10, 100, 1000, 10000, 100000})
    public void IUserRepository_insertMultiple_checkContains(int n_insertions) {
        Collection<NotificationUser> users = new ArrayList<>();

        for (int i = 0; i < n_insertions; ++i) {
            users.add(new NotificationUser(id + i, email + i));
        }

        userRepository.saveAll(users);

        Collection<NotificationUser> dbUsers = userRepository.findAll();

        Assertions.assertEquals(n_insertions, users.size());
        Assertions.assertEquals(n_insertions, dbUsers.size());

        boolean isDbUsersAll = dbUsers.stream().anyMatch(users::contains);
        boolean isUsersAll = users.stream().anyMatch(dbUsers::contains);

        Assertions.assertTrue(isDbUsersAll);
        Assertions.assertTrue(isUsersAll);
    }

    @Test
    public void IUserRepository_insertTwoUsers_sameId_shouldOverwrite() {
        String changedEmail = email + "2";

        NotificationUser user1 = new NotificationUser(id, email);
        NotificationUser user2 = new NotificationUser(id, changedEmail);

        userRepository.save(user1);
        userRepository.flush();

        userRepository.save(user2);
        userRepository.flush();

        Collection<NotificationUser> dbUsers = userRepository.findAllById(List.of(id));

        Assertions.assertEquals(1, dbUsers.size());
        Assertions.assertEquals(changedEmail, dbUsers.stream().findFirst().get().getEmail());
    }

    @Test
    public void IUserRepository_updateUser_existingId_shouldFail() {
        String otherId = id + 2;

        NotificationUser user1 = new NotificationUser(id, email);
        NotificationUser user2 = new NotificationUser(otherId, email);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.flush();

        Optional<NotificationUser> queryResult = userRepository.findById(user2.getId());
        Assertions.assertTrue(queryResult.isPresent());
        NotificationUser updateUser = queryResult.get();

        Executable exec = () -> {
            updateUser.setId(user1.getId());
            userRepository.flush();
        };

        Assertions.assertThrows(UncategorizedDataAccessException.class, exec);
    }

    @Test
    public void IUserRepository_updateUser_validCall() {
        String newEmail = "new@email.com";
        NotificationUser user1 = new NotificationUser(id, email);

        userRepository.save(user1);
        userRepository.flush();

        Optional<NotificationUser> queryResult = userRepository.findById(user1.getId());
        Assertions.assertTrue(queryResult.isPresent());
        NotificationUser updateUser = queryResult.get();

        updateUser.setEmail(newEmail);
        userRepository.flush();

        Optional<NotificationUser> queryResultAfterUpdate = userRepository.findById(user1.getId());
        Assertions.assertTrue(queryResultAfterUpdate.isPresent());
        NotificationUser afterUpdateUser = queryResultAfterUpdate.get();

        Assertions.assertEquals(newEmail, afterUpdateUser.getEmail());
    }

    @Test
    public void IUserRepository_deleteUser_validCall() {
        NotificationUser user = new NotificationUser(id, email);

        userRepository.save(user);
        userRepository.flush();
        userRepository.delete(user);

        Optional<NotificationUser> queryResult = userRepository.findById(user.getId());
        Assertions.assertTrue(queryResult.isEmpty());
    }
}
