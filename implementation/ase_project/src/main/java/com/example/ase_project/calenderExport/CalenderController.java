package com.example.ase_project.calenderExport;

import com.example.ase_project.calenderExport.ACalenderExportType;
import com.example.ase_project.calenderExport.EExportType;
import com.example.ase_project.calenderExport.ExportCalender;
import com.example.ase_project.calenderExport.UserCalender;
import org.springframework.web.bind.annotation.*;

@RestController
public class CalenderController {

    @GetMapping("/calender/{calenderType}")
    public String calenderExport(@PathVariable EExportType calenderType, @RequestBody UserCalender userCalender) throws Exception {
        //calendar with events must be set before
        ExportCalender exportCalender = new ExportCalender(calenderType);
        ACalenderExportType calender =  exportCalender.createExportCalender(calenderType, userCalender);
        return calender.convert();
    }

    @PostMapping("/calenderMapping")
    public UserCalender setCalender(@RequestBody UserCalender userCalender){
        return new UserCalender(userCalender.getRegisteredEvents(), userCalender.getBookmarkedEvents());
    }

}
