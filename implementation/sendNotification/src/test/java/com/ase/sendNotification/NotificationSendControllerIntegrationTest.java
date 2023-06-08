package com.ase.sendNotification;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ase.sendNotification.integration.RabbitMQSendNotificationConfiguration;
import com.ase.sendNotification.integration.RabbitMQUserConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

//@AutoConfigureMockMvc

@SpringBootTest
@TestPropertySource(properties =
        {"sendnotification.google.email=test@email.com",
                "sendnotification.google.password=test-password"})
@AutoConfigureMockMvc
public class NotificationSendControllerIntegrationTest {

    @MockBean
    private RabbitMQSendNotificationConfiguration sendNotificationConfiguration;

    @MockBean
    private RabbitMQUserConfiguration userConfiguration;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void NotificationSendControllerIntegrationTest_healthcheck() {
        try {
            this.mockMvc.perform(get("/api/v1/sendNotification/healthcheck")).andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

}
