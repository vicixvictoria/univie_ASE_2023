package com.ase.sendNotification;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.ase.sendNotification.rabbitmq.RabbitMQSendNotificationConfiguration;
import com.ase.sendNotification.rabbitmq.RabbitMQUserConfiguration;
import com.ase.sendNotification.repository.IUserRepository;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
