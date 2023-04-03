import CalenderExport.ACalenderExportType;
import CalenderExport.EExportType;
import CalenderExport.ExportCalender;
import CalenderExport.UserCalender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalenderExportTest {

    @Test
    public void createUserCalenderTest() {
        Date testDate = new Date();
        List<Date> registeredEvents = new ArrayList<>();
        List<Date> bookmarkedEvents = new ArrayList<>();
        registeredEvents.add(testDate);
        bookmarkedEvents.add(testDate);
        UserCalender userCalender = new UserCalender(registeredEvents, bookmarkedEvents);
        Assertions.assertEquals(userCalender.getBookmarkedEvents().size(), 1);
        Assertions.assertEquals(userCalender.getRegisteredEvents().size(), 1);
    }

    @Test
    public void exportToJSON() throws Exception {
        Date testDate = new Date();
        List<Date> registeredEvents = new ArrayList<>();
        List<Date> bookmarkedEvents = new ArrayList<>();
        registeredEvents.add(testDate);
        bookmarkedEvents.add(testDate);
        UserCalender userCalender = new UserCalender(registeredEvents, bookmarkedEvents);
        ExportCalender exportCalender = new ExportCalender(userCalender, EExportType.JSON);
        ACalenderExportType calenderExportType = exportCalender.createExportCalender(EExportType.JSON);
        String exportString = calenderExportType.convert();
        String testExportString = exportString.substring(2,18);
        Assertions.assertEquals(testExportString, "registeredEvents");
    }

    @Test
    public void exportToXML() throws Exception {
        Date testDate = new Date();
        List<Date> registeredEvents = new ArrayList<>();
        List<Date> bookmarkedEvents = new ArrayList<>();
        registeredEvents.add(testDate);
        bookmarkedEvents.add(testDate);
        UserCalender userCalender = new UserCalender(registeredEvents, bookmarkedEvents);
        ExportCalender exportCalender = new ExportCalender(userCalender, EExportType.XML);
        ACalenderExportType calenderExportType = exportCalender.createExportCalender(EExportType.XML);
        String exportString = calenderExportType.convert();
        String testExportString = exportString.substring(8,16);
        Assertions.assertEquals(testExportString, "register");
    }

    @Test
    public void exportToICAL() throws Exception{
        Date testDate = new Date();
        List<Date> registeredEvents = new ArrayList<>();
        List<Date> bookmarkedEvents = new ArrayList<>();
        registeredEvents.add(testDate);
        bookmarkedEvents.add(testDate);
        UserCalender userCalender = new UserCalender(registeredEvents, bookmarkedEvents);
        ExportCalender exportCalender = new ExportCalender(userCalender, EExportType.ICal);
        ACalenderExportType calenderExportType = exportCalender.createExportCalender(EExportType.ICal);
        String exportString = calenderExportType.convert();
        String testExportString = exportString.substring(0,16);
        Assertions.assertEquals(testExportString, "BEGIN:VCALENDAR\n");
    }
}
