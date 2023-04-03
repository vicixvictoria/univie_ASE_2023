package CalenderExport.Factory;

import CalenderExport.ACalenderExportType;
import CalenderExport.CalenderExportTypes.CalenderXMLExport;
import CalenderExport.UserCalender;

public class ConcreteCalenderExportFactoryXML extends ACalenderExportFactory {
    @Override
    public ACalenderExportType createExportCalender(UserCalender calender) {
        CalenderXMLExport calenderXMLExport = new CalenderXMLExport(calender);
        return calenderXMLExport;
    }
}