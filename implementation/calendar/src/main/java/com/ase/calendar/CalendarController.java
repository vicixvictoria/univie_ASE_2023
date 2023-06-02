package com.ase.calendar;

import java.util.List;
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
@RequestMapping(path = "api/v1/")
public class CalendarController {

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("calendar/{userId}/{calenderType}")
    public String calenderExport(@PathVariable String userId, @PathVariable EExportType calenderType) throws Exception {
        List<CalendarEvent>bookmarkedEvents = DataFetcher.requestBookmarkedEvents(userId);
        List<CalendarEvent>registeredEvents = DataFetcher.requestRegisteredEvent(userId);
        ExportCalendar exportCalendar = new ExportCalendar(calenderType);
        UserCalendar userCalendar = new UserCalendar(registeredEvents, bookmarkedEvents);
        ACalendarExportType calender = exportCalendar.createExportCalender(calenderType, userCalendar);
        return calender.convert();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/calendarMapping")
    public UserCalendar setCalender(@RequestBody UserCalendar userCalendar) {
        return new UserCalendar(userCalendar.getRegisteredEvents(),
                userCalendar.getBookmarkedEvents());
    }

    @GetMapping("calendar/healthCheck")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

}
