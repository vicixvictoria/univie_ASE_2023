package CalenderExport;

import CalenderExport.Factory.ACalenderExportFactory;
import CalenderExport.Factory.ConcreteCalenderExportFactoryICal;
import CalenderExport.Factory.ConcreteCalenderExportFactoryJSON;
import CalenderExport.Factory.ConcreteCalenderExportFactoryXML;

public class ExportCalender {

    private ACalenderExportFactory exportFactory;
    private ACalenderExportType calenderType;
    private EExportType exportType;
    private UserCalender userCalender;

    public ExportCalender(UserCalender calender, EExportType type) {
        this.userCalender = calender;
        this.exportType = type;
    }

    public ExportCalender(EExportType type){
        this.exportType = type;
    }
    public ACalenderExportType createExportCalender(EExportType exportType) throws Exception {
        switch (this.exportType) {
            case XML -> exportFactory = new ConcreteCalenderExportFactoryXML();
            case JSON -> exportFactory = new ConcreteCalenderExportFactoryJSON();
            case ICal -> exportFactory = new ConcreteCalenderExportFactoryICal();
            default -> {
                return null;
            }
        }
        return exportFactory.createExportCalender(this.userCalender);
    }


}
