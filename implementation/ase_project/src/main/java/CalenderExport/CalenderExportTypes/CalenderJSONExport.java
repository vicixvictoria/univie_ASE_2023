package CalenderExport.CalenderExportTypes;

import CalenderExport.ACalenderExportType;
import CalenderExport.UserCalender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CalenderJSONExport extends ACalenderExportType {
    public CalenderJSONExport(UserCalender calender) {
        super(calender);
    }

    @Override
    public String convert() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(calender);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
