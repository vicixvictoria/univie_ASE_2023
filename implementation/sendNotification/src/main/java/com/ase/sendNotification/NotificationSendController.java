package com.ase.sendNotification;

import com.ase.common.sendNotification.NotificationContent;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/sendNotification")
public class NotificationSendController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private final NotificationSendService notificationSendService;

    @Autowired
    public NotificationSendController(NotificationSendService notificationSendService) {
        this.notificationSendService = notificationSendService;
    }

    /**
     * Endpoint, which sends a notification with the content and to the address given in the
     * notificationContent
     *
     * @param notificationContent the content of the notification and the address to which it will
     *                            be sent
     */
    @PostMapping
    public void sendNotification(@RequestBody NotificationContent notificationContent) {
        LOGGER.info("GET /api/v1/sendNotification");
        notificationSendService.sendNotification(notificationContent.userId(),
                notificationContent.message());
    }
}
