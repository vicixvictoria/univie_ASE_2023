package CalenderExport.CalenderExportTypes;

import CalenderExport.ACalenderExportType;
import CalenderExport.UserCalender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.XML;

public class CalenderXMLExport extends ACalenderExportType {
    public CalenderXMLExport(UserCalender calender) {
        super(calender);
    }

    @Override
    public String convert() {
        ObjectMapper objectMapper = new ObjectMapper();
        String xml = "";
        try {
            xml = XML.toString(objectMapper.writeValueAsString(calender));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return xml;
    }
}
