package com.example.studentams;

public class StudentInfo {
    String FName;
    String LName;
    int age;
    String Branch;
    String Batch;
    String StudPass;
    String StudentID;
    String UName;

    public StudentInfo() {}

    public StudentInfo(String FName, String LName, int age, String branch, String batch, String studPass, String studentID, String UName) {
        this.FName = FName;
        this.LName = LName;
        this.age = age;
        Branch = branch;
        Batch = batch;
        StudPass = studPass;
        StudentID = studentID;
        this.UName = UName;
    }
}
