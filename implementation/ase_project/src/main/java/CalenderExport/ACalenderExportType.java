package CalenderExport;


import org.apache.catalina.User;

public abstract class ACalenderExportType {
    public UserCalender calender;
    public abstract String convert();
    public ACalenderExportType(UserCalender calender){
        this.calender = calender;
    }
}
