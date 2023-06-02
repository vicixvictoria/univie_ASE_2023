package com.ase.common.taggingEvent;

import java.io.Serializable;
import java.util.List;

public record TaggingEventMessage(String userId, String eventId, List<ETags> tags) implements Serializable {}
