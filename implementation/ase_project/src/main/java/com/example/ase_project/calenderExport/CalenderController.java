package com.example.ase_project.calenderExport;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalenderController {

    @GetMapping("/calender/{calenderType}")
    public String calenderExport(@PathVariable EExportType calenderType,
            @RequestBody UserCalender userCalender) throws Exception {
        //calendar with events must be set before
        ExportCalender exportCalender = new ExportCalender(calenderType);
        ACalenderExportType calender = exportCalender.createExportCalender(calenderType,
                userCalender);
        return calender.convert();
    }

    @PostMapping("/calenderMapping")
    public UserCalender setCalender(@RequestBody UserCalender userCalender) {
        return new UserCalender(userCalender.getRegisteredEvents(),
                userCalender.getBookmarkedEvents());
    }

}
