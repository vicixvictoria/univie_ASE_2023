package com.ase.common.bookmarkEvent;

import java.io.Serializable;


public record BookmarkEventMessage(String userId, String eventId) implements Serializable {

}
