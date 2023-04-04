package com.example.ase_project.sendnotification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NotificationSendController {

    private final NotificationSendService notificationSendService;

    @Autowired
    public NotificationSendController(NotificationSendService notificationSendService) {
        this.notificationSendService = notificationSendService;
    }

    @PostMapping(value = "/sendNotification")
    public void sendNotification(@RequestBody NotificationContent notificationContent) {
        notificationSendService.sendNotification(notificationContent.email(), notificationContent.message());
    }

}
