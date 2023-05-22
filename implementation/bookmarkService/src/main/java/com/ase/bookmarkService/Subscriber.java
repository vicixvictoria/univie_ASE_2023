package com.ase.bookmarkService;

import com.ase.common.RabbitMQMessage;
import com.ase.common.bookmarkEvent.BookmarkEventMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Subscriber {

    private final BookmarkService service;

    @Autowired
    public Subscriber(BookmarkService bookmarkService) {
        this.service = bookmarkService;
    }

    /**
     * triggers service based on selected method
     */
    public void bookmarkEventConsumer(RabbitMQMessage<BookmarkEventMessage> bookmarkEventMessage) {
        switch (bookmarkEventMessage.getMessageType()) {
            case UPDATE -> service.addBookmarkEvent(bookmarkEventMessage.getContent().userId(),
                    bookmarkEventMessage.getContent().eventId());
            case DELETE -> service.deleteBookmarkEvent(bookmarkEventMessage.getContent().eventId(),
                    bookmarkEventMessage.getContent().userId());
        }
    }

}
