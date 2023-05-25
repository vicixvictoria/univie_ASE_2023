package com.ase.common.attendance;

import java.io.Serializable;


public record AttendanceMessage(String userID, String eventID) implements Serializable {

}
