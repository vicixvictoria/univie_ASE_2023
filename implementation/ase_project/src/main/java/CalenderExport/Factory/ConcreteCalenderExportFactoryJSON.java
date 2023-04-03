package CalenderExport.Factory;

import CalenderExport.ACalenderExportType;
import CalenderExport.CalenderExportTypes.CalenderJSONExport;
import CalenderExport.UserCalender;

public class ConcreteCalenderExportFactoryJSON extends ACalenderExportFactory {
    @Override
    public ACalenderExportType createExportCalender(UserCalender calender) {
        CalenderJSONExport calenderJSONExport = new CalenderJSONExport(calender);
        return calenderJSONExport;
    }
}
