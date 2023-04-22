package com.example.ase_project.notification;

import com.example.ase_project.notification.model.NotificationService;
import com.example.ase_project.notification.model.data.NotificationEvent;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    private final NotificationService service;

    /**
     * Constructor for the controller
     *
     * @param service autowired service class
     */
    @Autowired
    public NotificationController(NotificationService service) {
        this.service = service;
    }

    /**
     * Endpoint, which gets events for which the user with userId will receive a notification
     *
     * @param userId the userId which will be queried in the system
     * @return the collection of events, for which the user with the given Id receives notifications
     */
    @GetMapping(value = "/{userId}")
    public Collection<NotificationEvent> getEvents(@PathVariable String userId) {
        LOGGER.info("GET /api/v1/notification/{userId}");
        return service.getEvents(userId);
    }

    /**
     * Endpoint, which given a userId and an event ques a notification
     *
     * @param userId id of the user who will get a notification
     * @param event  the event, about which will be notified
     */
    @PostMapping(value = "/{userId}")
    public void register(@PathVariable String userId, @RequestBody NotificationEvent event) {
        LOGGER.info("POST /api/v1/notification/{userId}");
        service.addEvent(userId, event);
    }

    /**
     * Endpoint, which updates the given event in the system and sends out notifications to all
     * registered user
     *
     * @param event the updated event. Must have the same Id as the to be updated event
     */
    @PutMapping
    public void update(@RequestBody NotificationEvent event) {
        LOGGER.info("PUT /api/v1/notification");
        service.updateEvent(event);
    }
}
