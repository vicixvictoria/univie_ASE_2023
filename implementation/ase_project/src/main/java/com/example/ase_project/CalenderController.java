package com.example.ase_project;

import CalenderExport.ACalenderExportType;
import CalenderExport.EExportType;
import CalenderExport.ExportCalender;
import CalenderExport.UserCalender;
import org.springframework.web.bind.annotation.*;

@RestController
public class CalenderController {

    @GetMapping("/calender/{calenderType}")
    public String calenderExport(@PathVariable EExportType calenderType) throws Exception {
        //calendar with events must be set before
        ExportCalender exportCalender = new ExportCalender(calenderType);
        ACalenderExportType calender =  exportCalender.createExportCalender(calenderType);
        return calender.convert();
    }

    @PostMapping("/calenderMapping")
    public UserCalender setCalender(@RequestBody UserCalender userCalender){
        return new UserCalender(userCalender.getRegisteredEvents(), userCalender.getBookmarkedEvents());
    }

}
