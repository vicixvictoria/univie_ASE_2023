package com.ase.calendar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarController {

    @GetMapping("/calender/{calenderType}")
    public String calenderExport(@PathVariable EExportType calenderType,
            @RequestBody UserCalendar userCalendar) throws Exception {
        //calendar with events must be set before
        ExportCalendar exportCalendar = new ExportCalendar(calenderType);
        ACalendarExportType calender = exportCalendar.createExportCalender(calenderType,
                userCalendar);
        return calender.convert();
    }

    @PostMapping("/calenderMapping")
    public UserCalendar setCalender(@RequestBody UserCalendar userCalendar) {
        return new UserCalendar(userCalendar.getRegisteredEvents(),
                userCalendar.getBookmarkedEvents());
    }

}
