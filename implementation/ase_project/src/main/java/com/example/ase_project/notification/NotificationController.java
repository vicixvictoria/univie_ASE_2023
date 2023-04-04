package com.example.ase_project.notification;

import com.example.ase_project.notification.model.NotificationService;
import com.example.ase_project.notification.model.data.NotificationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

// TODO: return HTTP status codes

@RestController
public class NotificationController {

    private final NotificationService service;

    @Autowired
    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping(value = "/notification/{userId}")
    public Collection<NotificationEvent> getEvents(@PathVariable String userId) {
        return service.getEvents(userId);
    }

    @PostMapping(value = "/notification/{userId}")
    public void register(@PathVariable String userId, @RequestBody NotificationEvent event) {
        service.addEvent(userId, event);
    }

    @PutMapping(value = "/notification")
    public void update(@RequestBody NotificationEvent event) {
        service.updateEvent(event);
    }
}
