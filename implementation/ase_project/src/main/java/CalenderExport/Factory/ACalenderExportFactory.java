package CalenderExport.Factory;

import CalenderExport.ACalenderExportType;
import CalenderExport.UserCalender;

public abstract class ACalenderExportFactory {
    public abstract ACalenderExportType createExportCalender(UserCalender calender);
}
