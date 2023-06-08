package com.ase.notification;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void NotificationSendControllerIntegrationTest_healthcheck() {
        try {
            this.mockMvc.perform(get("/api/v1/notification/healthcheck")).andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

}