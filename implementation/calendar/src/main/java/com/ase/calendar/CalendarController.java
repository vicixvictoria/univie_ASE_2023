package com.ase.calendar;

import java.lang.invoke.MethodHandles;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/calendar/")
public class CalendarController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    @GetMapping("export/{userId}/{calenderType}")
    public String calenderExport(@PathVariable String userId, @PathVariable EExportType calenderType) throws Exception {
        LOGGER.info("GET api/v1/calendar/export/{}/{}/", userId, calenderType);
        List<CalendarEvent>bookmarkedEvents = DataFetcher.requestBookmarkedEvents(userId);
        List<CalendarEvent>registeredEvents = DataFetcher.requestRegisteredEvent(userId);
        ExportCalendar exportCalendar = new ExportCalendar(calenderType);
        UserCalendar userCalendar = new UserCalendar(registeredEvents, bookmarkedEvents);
        ACalendarExportType calender = exportCalendar.createExportCalender(calenderType, userCalendar);
        return calender.convert();
    }

    @PostMapping("calendarMapping")
    public UserCalendar setCalender(@RequestBody UserCalendar userCalendar) {
        LOGGER.info("POST api/v1/calendar/calendarMapping");
        return new UserCalendar(userCalendar.getRegisteredEvents(),
                userCalendar.getBookmarkedEvents());
    }

    @GetMapping("healthCheck")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

}
