package com.ase.sendNotification.network;

import com.ase.common.user.User;
import com.ase.sendNotification.model.NotificationUser;

public class Converter {

    public NotificationUser toInternalUser(User user) {
        return new NotificationUser(user.id(), user.email());
    }

}
