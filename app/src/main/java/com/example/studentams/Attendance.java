package com.example.studentams;

public class Attendance {
    String SubjectName;
    int studentAttendance;
    int totalAttendanceTaken;
    float percentage;

    public Attendance() {
        SubjectName = "";
        studentAttendance = 0;
        totalAttendanceTaken = 0;
    }

    public Attendance(String str) {
        SubjectName = str;
        studentAttendance = 0;
        totalAttendanceTaken = 0;
        percentage = 0;
    }

    public Attendance(String subjectName, int studentAttendance, int totalAttendanceTaken) {
        SubjectName = subjectName;
        this.studentAttendance = studentAttendance;
        this.totalAttendanceTaken = totalAttendanceTaken;
        if(totalAttendanceTaken == 0){percentage = 0;} //To address the divide by 0 problem
        else{percentage = (float) studentAttendance/totalAttendanceTaken *100;}
    }

}
