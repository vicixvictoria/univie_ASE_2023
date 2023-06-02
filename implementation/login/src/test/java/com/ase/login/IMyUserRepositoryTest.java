package com.ase.login;

import com.ase.login.data.ERole;
import com.ase.login.data.MyUser;
import com.ase.login.repository.IMyUserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.UncategorizedDataAccessException;

@DataJpaTest
public class IMyUserRepositoryTest {

    private final String email = "some@email.com";
    private final String password = "some-password";
    private final ERole role = ERole.ATTENDEE;

    @Autowired
    IMyUserRepository userRepository;

    @Test
    public void IMyUserRepository_insertMyUser_validCall() {
        MyUser user = new MyUser(email, password, role);
        userRepository.save(user);
    }

    @Test
    public void IMyUserRepository_insertMyUser_checkContains() {
        MyUser user = new MyUser(email, password, role);
        userRepository.save(user);
        Assertions.assertTrue(userRepository.existsById(user.getId()));
    }

    @Test
    public void IMyUserRepository_insertTwoUsers_checkContains() {
        MyUser user1 = new MyUser(email, password, role);
        MyUser user2 = new MyUser(email + "2", password, role); // make emails different

        userRepository.save(user1);
        userRepository.save(user2);

        Assertions.assertTrue(userRepository.existsById(user1.getId()));
        Assertions.assertTrue(userRepository.existsById(user2.getId()));
    }

    @Test
    public void IMyUserRepository_insertTwoUsers_sameEmail_shouldFail() {
        MyUser user1 = new MyUser(email, password, role);
        MyUser user2 = new MyUser(email, password, role); // make emails different

        userRepository.save(user1);
        userRepository.flush();
        Executable exec = () -> {
            userRepository.save(user2);
            userRepository.flush();
        };

        Assertions.assertThrows(DataIntegrityViolationException.class, exec);
    }

    @Test
    public void IMyUserRepository_updateUser_validCall() {
        MyUser user = new MyUser(email, password, role);

        userRepository.save(user);

        Optional<MyUser> queryResult = userRepository.findById(user.getId());
        Assertions.assertTrue(queryResult.isPresent());
        MyUser updateUser = queryResult.get();

        updateUser.setEmail("newEmail");

        Optional<MyUser> queryResultAfterUpdate = userRepository.findById(user.getId());
        Assertions.assertTrue(queryResultAfterUpdate.isPresent());
        MyUser afterUpdateUser = queryResult.get();

        Assertions.assertEquals(updateUser.getEmail(), afterUpdateUser.getEmail());
    }

    @Test
    public void IMyUserRepository_updateUser_existingId_shouldFail() {
        MyUser user1 = new MyUser(email, password, role);
        MyUser user2 = new MyUser(email + "2", password, role);

        userRepository.save(user1);
        userRepository.save(user2);

        Optional<MyUser> queryResult = userRepository.findById(user1.getId());
        Assertions.assertTrue(queryResult.isPresent());
        MyUser updateUser1 = queryResult.get();

        Executable exec = () -> {
            updateUser1.setId(user2.getId());
            userRepository.flush();
        };
        Assertions.assertThrows(UncategorizedDataAccessException.class, exec);
    }

    @Test
    public void IMyUserRepository_updateUser_existingEmail_shouldFail() {
        MyUser user1 = new MyUser(email, password, role);
        MyUser user2 = new MyUser(email + "2", password, role);

        userRepository.save(user1);
        userRepository.save(user2);

        Optional<MyUser> queryResult = userRepository.findById(user1.getId());
        Assertions.assertTrue(queryResult.isPresent());
        MyUser updateUser1 = queryResult.get();

        Executable exec = () -> {
            updateUser1.setEmail(user2.getEmail());
            userRepository.flush();
        };
        Assertions.assertThrows(DataIntegrityViolationException.class, exec);
    }

    @Test
    public void IMyUserRepository_deleteUser_validCall() {
        MyUser user1 = new MyUser(email, password, role);

        userRepository.save(user1);

        userRepository.delete(user1);

        Optional<MyUser> queryResult = userRepository.findById(user1.getId());
        Assertions.assertTrue(queryResult.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1000, 10000, 100000})
    public void IMyUserRepository_insertUsers_validCall(int TO_INSERT) {
        Collection<MyUser> users = new ArrayList<>();

        for (int i = 0; i < TO_INSERT; ++i) {
            users.add(new MyUser(email + i, password, role));
        }

        userRepository.saveAll(users);

        Collection<MyUser> dbUsers = userRepository.findAll();

        Assertions.assertEquals(TO_INSERT, users.size());
        Assertions.assertEquals(TO_INSERT, dbUsers.size());

        boolean isDbUsersAll = dbUsers.stream().anyMatch(users::contains);
        boolean isUsersAll = users.stream().anyMatch(dbUsers::contains);

        Assertions.assertTrue(isDbUsersAll);
        Assertions.assertTrue(isUsersAll);
    }

}
