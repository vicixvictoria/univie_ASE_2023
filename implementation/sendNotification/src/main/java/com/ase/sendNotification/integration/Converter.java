package com.ase.sendNotification.integration;

import com.ase.common.user.User;
import com.ase.sendNotification.domain.NotificationUser;

public class Converter {

    public NotificationUser toInternalUser(User user) {
        return new NotificationUser(user.id(), user.email());
    }

}
