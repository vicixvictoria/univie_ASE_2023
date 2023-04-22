package com.example.ase_project.attendance;

import static com.example.ase_project.attendance.AttendanceService.attend;
import static com.example.ase_project.attendance.AttendanceService.attendance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceServiceTest {

    @Test
    public void attendanceTest() {
        Assertions.assertEquals(attend("100"), 1);
        Assertions.assertEquals(attendance("100"), 1);
        Assertions.assertEquals(attendance("200"), 0);
    }
}
