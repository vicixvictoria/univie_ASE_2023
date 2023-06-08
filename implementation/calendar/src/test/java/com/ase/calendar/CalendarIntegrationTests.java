package com.ase.calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CalendarController.class)
@AutoConfigureTestDatabase
public class CalendarIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCalendarExport_dataFetcher() throws Exception {
        mockMvc.perform(get("/api/v1/calendar/export/testUser/JSON").contentType(
                MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
