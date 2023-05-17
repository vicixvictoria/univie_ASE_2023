package com.ase.login;

import com.ase.login.data.MyUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

public class PublisherTests {

    @Value("${user.exchange}")
    private String userExchange;
    private RabbitTemplate rabbitTemplateMock;
    private Publisher publisher;

    private MyUser getMockUser() {
        return Mockito.mock(MyUser.class);
    }

    @BeforeEach
    public void setPublisher() {
        rabbitTemplateMock = Mockito.mock(RabbitTemplate.class);
        publisher = new Publisher(rabbitTemplateMock);
    }

    @Test
    public void Publisher_newUserCreated_validCall() {
        MyUser testUser = getMockUser();
        publisher.newUserCreated(testUser);
        Mockito.verify(rabbitTemplateMock, Mockito.times(1))
                .convertAndSend(userExchange, "", testUser);
    }

    @Test
    public void Publisher_userUpdated_validCall() {
        MyUser testUser = getMockUser();
        publisher.userUpdated(testUser);
        Mockito.verify(rabbitTemplateMock, Mockito.times(1))
                .convertAndSend(userExchange, "", testUser);
    }
}
