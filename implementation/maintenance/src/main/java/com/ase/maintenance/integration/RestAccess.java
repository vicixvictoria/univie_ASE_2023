package com.ase.maintenance.integration;

import com.ase.maintenance.business.Availability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestAccess {
    private static final String ANALYTICREPORT_ENDPOINT = "${ANALYTICREPORT-ENDPOINT}/api/v1/analyticReport/";
    private static final String ATTENDANCE_ENDPOINT = "${ATTENDANCE-ENDPOINT}/api/v1/attendance/";
    private static final String BOOKMARK_ENDPOINT = "${BOOKMARK-ENDPOINT}/api/v1/bookmark/healthcheck";
    private static final String CALENDAR_ENDPOINT = "${CALENDAR-ENDPOINT}/api/v1/calendar/healthcheck";
    private static final String EVENT_ENDPOINT = "${EVENT-ENDPOINT}/api/v1/event/";
    private static final String FEEDBACK_ENDPOINT = "${FEEDBACK-ENDPOINT}/api/v1/feedback/event/";
    private static final String LOGIN_ENDPOINT = "${LOGIN-ENDPOINT}/api/v1/login/healthcheck";
    private static final String NOTIFICATION_ENDPOINT = "${NOTIFICATION-ENDPOINT}/api/v1/notification/healthcheck";
    private static final String RECOMMENDER_ENDPOINT = "${RECOMMENDER-ENDPOINT}/api/v1/recommender/healthcheck";
    private static final String SEARCH_EVENT_ENDPOINT = "${SEARCHSERVICEEVENT-ENDPOINT}/api/v1/searchService/healthcheck";
    private static final String SEND_NOTIFICATION_ENDPOINT = "${SENDNOTIFICATION-ENDPOINT}/api/v1/sendNotification/healthcheck";
    private static final String TAGGING_ENDPOINT = "${TAGGINGSERVICE-ENDPOINT}/api/v1/tagging/healthcheck";
    private final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    public RestAccess() {}

    /**
     * access the endpoint and checks if the endpoint is available
     * @param endpoint: the endpoint to check
     * @param hostname: the hostname that should be stored in the returnes Availability object
     * @return Availability object
     */
    private Availability getAvailability(String endpoint, String hostname) {
        Availability availability = null;

        try {
            ResponseEntity<Void> response = REST_TEMPLATE.getForEntity(endpoint, Void.class);
            availability = new Availability(hostname, response.getStatusCode().value());
        }
        catch (ResourceAccessException | HttpClientErrorException | IllegalArgumentException |NullPointerException exception) {
            LOGGER.warn("MaintenanceService: host {hostname} is not available");
            availability = new Availability(hostname, HttpStatus.NOT_FOUND.value());
        }
        return availability;

    }

    public ArrayList<Availability> getAllAvailabilities() {
        Availability attendanceAvailability = getAvailability(ATTENDANCE_ENDPOINT, "attendance");
        Availability bookmarkAvailability = getAvailability(BOOKMARK_ENDPOINT, "bookmark");
        Availability calendarAvailability = getAvailability(CALENDAR_ENDPOINT, "calendar");
        Availability eventAvailability = getAvailability(EVENT_ENDPOINT, "event");
        Availability feedbackAvailability = getAvailability(FEEDBACK_ENDPOINT, "feedback");
        Availability loginAvailability = getAvailability(LOGIN_ENDPOINT, "login");
        Availability notificationAvailability = getAvailability(NOTIFICATION_ENDPOINT, "notification");
        Availability recommenderAvailability = getAvailability(RECOMMENDER_ENDPOINT, "recommender");
        Availability searchEventAvailability = getAvailability(SEARCH_EVENT_ENDPOINT, "searchEvent");
        Availability sendNotificationAvailability = getAvailability(SEND_NOTIFICATION_ENDPOINT, "sendNotification");
        Availability taggingAvailability = getAvailability(TAGGING_ENDPOINT, "tagging");

        ArrayList<Availability> ret = new ArrayList<>();
        ret.add(attendanceAvailability);
        ret.add(bookmarkAvailability);
        ret.add(calendarAvailability);
        ret.add(eventAvailability);
        ret.add(feedbackAvailability);
        ret.add(loginAvailability);
        ret.add(notificationAvailability);
        ret.add(recommenderAvailability);
        ret.add(searchEventAvailability);
        ret.add(sendNotificationAvailability);
        ret.add(taggingAvailability);

        return ret;
    }
}
