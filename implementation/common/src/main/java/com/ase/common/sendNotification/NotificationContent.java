package com.ase.common.sendNotification;

import java.io.Serializable;

public record NotificationContent(String userId, String message) implements Serializable {

}