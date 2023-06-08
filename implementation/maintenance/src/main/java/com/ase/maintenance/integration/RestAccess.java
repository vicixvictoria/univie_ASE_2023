package com.ase.maintenance.integration;

import com.ase.maintenance.business.Availability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${analyticReport.endpoint}")
    private String ANALYTICREPORT_ENDPOINT;
    @Value("${attendance.endpoint}")
    private String ATTENDANCE_ENDPOINT;
    @Value("${bookmark.endpoint}")
    private String BOOKMARK_ENDPOINT;
    @Value("${calendar.endpoint}")
    private String CALENDAR_ENDPOINT;
    @Value("${event.endpoint}")
    private String EVENT_ENDPOINT;
    @Value("${feedback.endpoint}")
    private String FEEDBACK_ENDPOINT;
    @Value("${login.endpoint}")
    private String LOGIN_ENDPOINT;
    @Value("${notification.endpoint}")
    private String NOTIFICATION_ENDPOINT;
    @Value("${recommender.endpoint}")
    private String RECOMMENDER_ENDPOINT;
    @Value("${searchService.endpoint}")
    private String SEARCH_EVENT_ENDPOINT;
    @Value("${sendnotification.endpoint}")
    private String SEND_NOTIFICATION_ENDPOINT;
    //@Value("${tagging.endpoint}")
    //private String TAGGING_ENDPOINT;
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
        LOGGER.info(endpoint);
        try {
            ResponseEntity<Void> response = REST_TEMPLATE.getForEntity(endpoint, Void.class);
            availability = new Availability(hostname, response.getStatusCode().value());
        }
        catch (ResourceAccessException | HttpClientErrorException | IllegalArgumentException |NullPointerException exception) {
            LOGGER.warn("MaintenanceService: host " + hostname + " is not available");
            LOGGER.warn(exception.getMessage());
            availability = new Availability(hostname, HttpStatus.NOT_FOUND.value());
        }
        return availability;

    }

    public ArrayList<Availability> getAllAvailabilities() {
        Availability analyticReportAvailability = getAvailability(ANALYTICREPORT_ENDPOINT, "analyticReport");
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
        //Availability taggingAvailability = getAvailability(TAGGING_ENDPOINT, "tagging");

        ArrayList<Availability> ret = new ArrayList<>();
        ret.add(analyticReportAvailability);
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
        //ret.add(taggingAvailability);

        return ret;
    }
}
