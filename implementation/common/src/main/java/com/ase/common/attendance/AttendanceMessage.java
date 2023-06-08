package com.ase.common.attendance;

import java.io.Serializable;

/**
 * The AttendanceMessage class represents a message containing the user ID and event ID for attendance.
 * It is used for communication between components in the attendance system.
 */
public record AttendanceMessage(String userID, String eventID) implements Serializable {

}
