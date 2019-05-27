package com.example.studentams;

public class Attendance {
    String studentID;
    int studentAttendance;
    int totalAttendanceTaken;

    public Attendance() {
        studentID = "";
        studentAttendance = 0;
        totalAttendanceTaken = 0;
    }

    public Attendance(String studentID, int studentAttendance, int totalAttendanceTaken) {
        this.studentID = studentID;
        this.studentAttendance = studentAttendance;
        this.totalAttendanceTaken = totalAttendanceTaken;
    }
}
