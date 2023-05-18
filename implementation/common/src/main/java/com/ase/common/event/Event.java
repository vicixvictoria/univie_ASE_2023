package com.ase.common.event;

import java.io.Serializable;
import java.util.Date;

public record Event(String eventID, EEventType type, int capacity, Date date, String description,
                    String organizerID, String eventName) implements Serializable { }
