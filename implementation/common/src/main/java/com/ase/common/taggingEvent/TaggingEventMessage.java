package com.ase.common.taggingEvent;

import java.io.Serializable;

public record TaggingEventMessage(String userId, String eventId, ETags tags) implements Serializable {}
