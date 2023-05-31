package com.ase.sendNotification;

import com.ase.sendNotification.mailsender.AMailSender;
import com.ase.sendNotification.model.NotificationUser;
import com.ase.sendNotification.repository.IUserRepository;
import java.lang.invoke.MethodHandles;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class NotificationSendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private final AMailSender mailSender;
    private final IUserRepository userRepository;

    @Autowired
    public NotificationSendService(@Qualifier("google") AMailSender mailSender,
            IUserRepository userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    /**
     * Sends a notification with the message to the given email
     *
     * @param userId  the userId of the recipient of the notification
     * @param message the message of the notification
     */
    public void sendNotification(String userId, String message) {
        Optional<NotificationUser> userResult = userRepository.findById(userId);
        if (userResult.isEmpty()) {
            LOGGER.warn(String.format(
                    "Tried sending a notification to a user which does not exist in the local database. UserId: %s",
                    userId));
            assert false;
            return; // fail gracefully
        }
        mailSender.send(userResult.get().getEmail(), message);
    }

    public void newUser(NotificationUser user) {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(String.format(
                    "Tried saving a user which is already saved in the database! UserId: %s",
                    user.getId()));
            assert false;
            return; // fail gracefully
        }
    }

    public void updateUser(NotificationUser user) {
        Optional<NotificationUser> oldUserResult = userRepository.findById(user.getId());
        if (oldUserResult.isEmpty()) {
            LOGGER.warn(String.format(
                    "Tried updating a user which does not exist in local database. UserId: %s",
                    user.getId()));
            assert false;
            return; // fail gracefully
        }
        NotificationUser oldUser = oldUserResult.get();
        oldUser.setEmail(user.getEmail());
    }

    public void deleteUser(NotificationUser user) {
        try {
            userRepository.delete(user);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(String.format(
                    "Tried deleting a user which does not exist in the database! UserId: %s",
                    user.getId()));
            assert false;
            return;
        }
    }
}
