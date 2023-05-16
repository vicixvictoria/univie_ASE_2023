package com.ase;

import java.io.Serializable;

public class RabbitMQMessage<Content extends Serializable> implements Serializable {

    private final EMessageType messageType;
    private final Content content;

    public RabbitMQMessage(EMessageType messageType, Content content) {
        this.messageType = messageType;
        this.content = content;
    }

    public EMessageType getMessageType() {
        return messageType;
    }

    public Content getContent() {
        return content;
    }
}
