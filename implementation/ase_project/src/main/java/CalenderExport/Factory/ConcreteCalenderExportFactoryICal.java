package CalenderExport.Factory;

import CalenderExport.ACalenderExportType;
import CalenderExport.CalenderExportTypes.CalenderICalExport;
import CalenderExport.UserCalender;

public class ConcreteCalenderExportFactoryICal extends ACalenderExportFactory {

    @Override
    public ACalenderExportType createExportCalender(UserCalender calender) {
        CalenderICalExport calenderICalExport = new CalenderICalExport(calender);
        return calenderICalExport;
    }
}
