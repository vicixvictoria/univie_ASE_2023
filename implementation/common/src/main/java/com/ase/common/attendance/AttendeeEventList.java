package com.ase.common.attendance;

import java.io.Serializable;
import java.util.List;

/**
 * The AttendeeEventList class represents a list of event IDs associated with an attendee.
 * It is used to store and retrieve the list of events attended by an attendee.
 */
public record AttendeeEventList(String userID, List<String> eventIDs) implements Serializable {

}
